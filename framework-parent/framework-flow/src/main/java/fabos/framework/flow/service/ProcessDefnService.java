package fabos.framework.flow.service;

import java.util.List;
import java.util.Map;

import fabos.framework.core.orm.model.Flowable;
import fabos.framework.core.orm.service.BaseService;
import fabos.framework.flow.model.ProcessDefinition;

public interface ProcessDefnService extends BaseService<ProcessDefinition, String> {

	ProcessDefinition getProcessByName(String processName);
	
	List<String> getAllOperationIds(String processId);
	
	Flowable getFirstStep(Flowable flowEntity, Map<String, Object> variableMap);
	
	Flowable getNextStep(Flowable flowEntity, Map<String, Object> variableMap);
}
