package fabos.framework.core.orm.service;

import java.io.Serializable;
import java.util.List;

import fabos.framework.core.orm.model.Page;

public interface BaseService<T, PK extends Serializable> {

	int save(T entity);

	int update(T entity);
	
	int delete(T entity);

	T getById(PK id);

	int deleteById(PK id);

	Page<T> selectByPage(Page<T> page);

	List<T> selectPage(int pageNum, int pageSize);
	
}
