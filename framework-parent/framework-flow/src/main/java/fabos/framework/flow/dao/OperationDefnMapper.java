package fabos.framework.flow.dao;

import java.util.List;

import fabos.framework.core.orm.dao.BatchMapper;
import fabos.framework.flow.model.OperationDefinition;

public interface OperationDefnMapper extends BatchMapper<OperationDefinition, String> {

	public List<OperationDefinition> getListByCondition(OperationDefinition opDern);
	
	public String getDetailOperationType(String lotName);
	
	/**
	 * <p>连接flow_op_chk和flow_op_defn两张表</p>
	 * <p>以FLOW_OP_CHK .OP_ID=PROC_OP_NAME & FLOW_OP_CHK. RULE_ID=FLOW_RULE_DEFN. ID</p>
	 * @param operationId
	 * @return
	 * @author 杨其洪
	 */
	public List<OperationDefinition> getListByOpCheck(String proccessOperationName);
}
