package fabos.framework.core.orm.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

import fabos.framework.core.exception.BizServiceException;
import fabos.framework.core.exception.CommonErrorCode;
import fabos.framework.core.orm.dao.BaseMapper;
import fabos.framework.core.orm.model.Page;
import fabos.framework.core.orm.service.BaseService;

public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

	@Autowired
	protected BaseMapper<T> mapper;

	@Override
	public int save(T entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	public int update(T entity) {
		int result = mapper.updateByPrimaryKey(entity);
		if (result == 0) {
			throw new BizServiceException(CommonErrorCode.VERSION_CHECK_FAILED);
		}
		return result;
	}
	
	@Override
	public int delete(T entity) {
		int result = mapper.delete(entity);
		if (result == 0) {
			throw new BizServiceException(CommonErrorCode.VERSION_CHECK_FAILED);
		}
		return result;
	}

	@Override
	public T getById(PK id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteById(PK id) {
		int result = mapper.deleteByPrimaryKey(id);
		if (result == 0) {
			throw new BizServiceException(CommonErrorCode.VERSION_CHECK_FAILED);
		}
		return result;
	}
	
	@Override
	public List<T> selectPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return mapper.selectAll();
	}
	

	@Override
	public Page<T> selectByPage(Page<T> page) {
		PageHelper.startPage(page.getPageNo(), page.getPageSize());
		List<T> result = mapper.selectAll();

		 //分页时，实际返回的结果list类型是Page<T>，如果想取出分页信息，需要强制转换为Page<T>
		com.github.pagehelper.Page<T> p = (com.github.pagehelper.Page<T>) result;
		
		page.setResult(result);
		page.setTotalCount(p.getTotal());

		return page;
	}

}
