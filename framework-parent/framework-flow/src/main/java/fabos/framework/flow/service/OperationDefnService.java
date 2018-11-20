package fabos.framework.flow.service;

import java.util.List;

import fabos.framework.core.orm.service.BatchService;
import fabos.framework.flow.model.OperationDefinition;

public interface OperationDefnService extends BatchService<OperationDefinition, String> {

	/**
	 * 根据optype 获取站点描述信息
	 *
	 * @author : Pansy
	 *
	 * @Date : 20170804
	 * @return
	 */
	List<OperationDefinition> getOpNamesList(String factoryName, String opType);

	/**
	 * <p>获取站点详细信息</p>
	 * @return
	 * @author 张天屹
	 */
	String getDetailOperationType(String lotName);

	/**
	 * <p>连接flow_op_chk和flow_op_defn两张表</p>
	 * <p>以FLOW_OP_CHK .OP_ID=PROC_OP_ID & FLOW_OP_CHK. RULE_ID=FLOW_RULE_DEFN. ID</p>
	 * @param proccessOperationName
	 * @return
	 * @author 杨其洪
	 */
	public List<OperationDefinition> getListByOpCheck(String proccessOperationId);

}
