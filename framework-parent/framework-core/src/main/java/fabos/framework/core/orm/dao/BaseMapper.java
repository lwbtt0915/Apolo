package fabos.framework.core.orm.dao;

import tk.mybatis.mapper.common.Mapper;

/**
 * 通用Mapper接口,其他接口继承该接口即可
 *
 * @param <T> 不能为空
 * @author Bruno
 */
public interface BaseMapper<T> extends Mapper<T> {

	
}
