package fabos.framework.flow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import fabos.framework.core.orm.service.impl.BatchServiceImpl;
import fabos.framework.flow.dao.OperationDefnMapper;
import fabos.framework.flow.model.OperationDefinition;
import fabos.framework.flow.service.OperationDefnService;

@Service("operationDefnService")
public class OperationDefnServiceImpl extends BatchServiceImpl<OperationDefinition, String> implements OperationDefnService {//@formatter:off

	@Override
	public List<OperationDefinition> getOpNamesList(String factoryName, String opType) {

		OperationDefinition opDern = new OperationDefinition();
		opDern.setFactoryName(factoryName);
		opDern.setOperationName(opType);

		List<OperationDefinition> list = ((OperationDefnMapper) mapper).getListByCondition(opDern);

		return list;
	}

	@Override
	public String getDetailOperationType(String lotName) {
		return ((OperationDefnMapper) mapper).getDetailOperationType(lotName);
	}

	
	/**
	 * <p>连接flow_op_chk和flow_op_defn两张表</p>
	 * <p>以FLOW_OP_CHK .OP_ID=PROC_OP_NAME & FLOW_OP_CHK. RULE_ID=FLOW_RULE_DEFN. ID</p>
	 * @param proccessOperationName
	 * @return
	 * @author 杨其洪
	 */
	public List<OperationDefinition> getListByOpCheck(String proccessOperationName){
		List<OperationDefinition> list = ((OperationDefnMapper) mapper).getListByOpCheck(proccessOperationName);
		return list;
	}
}
