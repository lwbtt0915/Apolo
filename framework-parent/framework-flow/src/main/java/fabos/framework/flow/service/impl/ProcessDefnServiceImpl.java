package fabos.framework.flow.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.InclusiveGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.ReceiveTask;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.Task;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.util.io.BytesStreamSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import fabos.framework.core.exception.BizServiceException;
import fabos.framework.core.exception.CommonErrorCode;
import fabos.framework.core.orm.model.Flowable;
import fabos.framework.core.orm.service.impl.BaseServiceImpl;
import fabos.framework.flow.dao.ProcessDefnMapper;
import fabos.framework.flow.model.ProcessDefinition;
import fabos.framework.flow.service.ProcessDefnService;

@Service("processDefnService")
public class ProcessDefnServiceImpl extends BaseServiceImpl<ProcessDefinition, String> implements ProcessDefnService {

	private Map<String, Process> bpmnProcessMap;

	private static final String PROCESS_STATE_ACTIVE = "ACTIVE";

	private static final Logger logger = LoggerFactory.getLogger(ProcessDefnServiceImpl.class);

	@PostConstruct
	public void initialize() {
		ProcessDefinition exampleProcess = new ProcessDefinition();
		exampleProcess.setActiveState(PROCESS_STATE_ACTIVE);
		List<ProcessDefinition> processes = super.mapper.selectAll();

		bpmnProcessMap = new HashMap<>(processes.size());
		BpmnXMLConverter converter = new BpmnXMLConverter();
		for (ProcessDefinition process : processes) {
			if (StringUtils.isNotBlank(new String(process.getBpmnXml()))) {
				BpmnModel bpmnModel = converter.convertToBpmnModel(new BytesStreamSource(process.getBpmnXml()), false, false);
				bpmnProcessMap.put(process.getProcessId(), bpmnModel.getMainProcess());
			}
		}
		logger.info("bpmn model map initialized.");
	}

	@Override
	public Flowable getFirstStep(Flowable flowEntity, Map<String, Object> variableMap) {
		Process process = this.fetchProcess(flowEntity);
		StartEvent start = process.findFlowElementsOfType(StartEvent.class).get(0);
		flowEntity.setProcessOpId(start.getId());
		return getNextStep(flowEntity, variableMap);
	}

	@Override
	public ProcessDefinition getProcessByName(String processName) {
		ProcessDefinition processCon = new ProcessDefinition();
		processCon.setProcessId(processName);
		return ((ProcessDefnMapper) mapper).selectOne(processCon);
	}

	@Override
	public List<String> getAllOperationIds(String processId) {
		Process bpmnProcess = bpmnProcessMap.get(processId);
		if (bpmnProcess == null) {
			return Collections.emptyList();
		}

		List<String> operationIds = Lists.newArrayList();
		List<Pair<String, Integer>> pairs = Lists.newArrayList();

		Collection<ReceiveTask> elements = bpmnProcess.findFlowElementsOfType(ReceiveTask.class, false);
		for (ReceiveTask taskNode : elements) {
			String seq = taskNode.getDocumentation().trim();
			pairs.add(Pair.of(taskNode.getId(), Integer.parseInt(seq)));
		}

		Collections.sort(pairs, new Comparator<Pair<String, Integer>>() {
			@Override
			public int compare(Pair<String, Integer> p1, Pair<String, Integer> p2) {
				return p1.getRight().compareTo(p2.getRight());
			}
		});

		operationIds = Lists.transform(pairs, new Function<Pair<String, Integer>, String>() {
			@Override
			public String apply(Pair<String, Integer> input) {
				return input.getLeft();
			}
		});

		return operationIds;
	}

	@Override
	public Flowable getNextStep(Flowable flowEntity, Map<String, Object> variableMap) {
		Process process = this.fetchProcess(flowEntity);
		if (process == null) {
			throw new BizServiceException(CommonErrorCode.NO_VALID_ENUM_DEFN_FOUND).set("未获取到该流程图",
					"ProcessId:" + flowEntity.getProcessId());
		}
		String currentNode = flowEntity.getProcessOpId();
		FlowNode sourceNode = (FlowNode) process.getFlowElement(currentNode);

		if (sourceNode == null) {
			throw new BizServiceException(CommonErrorCode.NO_VALID_ENUM_DEFN_FOUND).set("该流程与该站点不匹配",
					"ProcessId:" + flowEntity.getProcessId() + " ProcessOpId:" + currentNode);
		}

		if (sourceNode instanceof ExclusiveGateway) {
			List<SequenceFlow> branches = sourceNode.getOutgoingFlows();
			String targetRef = ((ExclusiveGateway) sourceNode).getDefaultFlow();
			for (SequenceFlow branch : branches) {
				String condition = branch.getConditionExpression();
				if ((condition != null) && checkBranchCondition(condition, variableMap)) {
					targetRef = branch.getTargetRef();

					FlowNode newSourceNode = (FlowNode) process.getFlowElement(targetRef);
					if (newSourceNode instanceof ExclusiveGateway) {
						flowEntity.setProcessOpId(targetRef);
						getNextStep(flowEntity, variableMap);
					}
					break;
				}
			}
			flowEntity.setProcessOpId(targetRef);
			return checkTarget(process, flowEntity, variableMap);
		} else {
			SequenceFlow flow = sourceNode.getOutgoingFlows().get(0);
			flowEntity.setProcessOpId(flow.getTargetRef());
			return checkTarget(process, flowEntity, variableMap);
		}
	}

	private Flowable checkTarget(Process process, Flowable flowEntity, Map<String, Object> variableMap) {
		String targetRef = flowEntity.getProcessOpId();
		FlowElement target = process.getFlowElement(targetRef);
		if (target instanceof Task) {
			flowEntity.setProcessOpName(target.getName());
			return flowEntity;
		} else if ((target instanceof ExclusiveGateway) || (target instanceof InclusiveGateway)) {
			return getNextStep(flowEntity, variableMap);
		} else if (target instanceof CallActivity) {
			String subProcessKey = ((CallActivity) target).getCalledElement();
			// TODO subProcess是否为rework流程，reworkcount+1
			String subprocess = subProcessKey, parentOpId = target.getId();
			if (flowEntity.getSubProcessId() != null) {
				subprocess = flowEntity.getSubProcessId() + "." + subProcessKey;
				parentOpId = flowEntity.getTopProcessOpId() + "." + target.getId();
			}

			flowEntity.setSubProcessId(subprocess);
			flowEntity.setTopProcessOpId(parentOpId);

			return getFirstStep(flowEntity, variableMap);
		} else if (target instanceof EndEvent) {
			if (flowEntity.getSubProcessId() == null) {
				flowEntity.setProcessOpId(target.getName());
				return flowEntity;
			} else {
				String opId = flowEntity.getTopProcessOpId();
				String subProcess = null, parentOpId = null;
				int index = StringUtils.lastIndexOf(flowEntity.getSubProcessId(), ".");
				if (index != -1) {
					subProcess = StringUtils.substringBeforeLast(flowEntity.getSubProcessId(), ".");
					opId = StringUtils.substringAfterLast(flowEntity.getTopProcessOpId(), ".");
					parentOpId = StringUtils.substringBeforeLast(flowEntity.getTopProcessOpId(), ".");
				}

				flowEntity.setProcessOpId(opId);
				flowEntity.setSubProcessId(subProcess);
				flowEntity.setTopProcessOpId(parentOpId);
				return getNextStep(flowEntity, variableMap);
			}
		}
		return flowEntity;
	}

	private boolean checkBranchCondition(String condition, Map<String, Object> variableMap) {
		ExpressionFactory factory = new ExpressionFactoryImpl();
		SimpleContext context = new SimpleContext();

		for (String key : variableMap.keySet()) {
			Object value = variableMap.get(key);
			if (null != value) {
				context.setVariable(key, factory.createValueExpression(value, value.getClass()));
			}
		}

		ValueExpression ex = factory.createValueExpression(context, condition, Boolean.class);
		return (Boolean) ex.getValue(context);
	}

	private Process fetchProcess(Flowable flowEntity) {
		String processKey = flowEntity.getProcessId();
		if (StringUtils.isNotBlank(flowEntity.getSubProcessId())) {
			processKey = flowEntity.getSubProcessId();
			int index = StringUtils.lastIndexOf(processKey, ".");
			if (index != -1) {
				processKey = StringUtils.substringAfterLast(processKey, ".");
			}
		}
		return bpmnProcessMap.get(processKey);
	}

}
