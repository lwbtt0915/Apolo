package fabos.framework.core.orm.service;

import java.io.Serializable;
import java.util.List;

public interface BatchService<T, PK extends Serializable> extends BaseService<T, PK> {

	/**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等
     * <p>
     * 不支持主键策略，插入前需要设置好主键的值
     * <p>
     */
	int doBatchInsert(List<T> entities);

	/**
	 * 根据主键字符串进行查询，类中只存在一个带有@Id注解的字段
	 */
	List<T> selectByIdList(List<PK> idList);

	/**
	 * 根据主键字符串进行删除，类中只存在一个带有@Id注解的字段
	 */
	int deleteByIdList(List<PK> idList);
}
