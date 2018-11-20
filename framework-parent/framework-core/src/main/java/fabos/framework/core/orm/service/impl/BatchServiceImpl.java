package fabos.framework.core.orm.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

import fabos.framework.core.exception.BizServiceException;
import fabos.framework.core.exception.CommonErrorCode;
import fabos.framework.core.orm.dao.BatchMapper;
import fabos.framework.core.orm.model.Page;
import fabos.framework.core.orm.service.BatchService;

public abstract class BatchServiceImpl<T, PK extends Serializable> implements BatchService<T, PK> {

	@Autowired
	protected BatchMapper<T, PK> mapper;

	@Override
	public int doBatchInsert(List<T> entities) {
		return mapper.insertList(entities);
	}
	
	/**
	 * 根据主键字符串进行查询，类中只存在一个带有@Id注解的字段
	 */
	public List<T> selectByIdList(List<PK> idList) {
		return mapper.selectByIdList(idList);
	}

	/**
	 * 根据主键字符串进行删除，类中只存在一个带有@Id注解的字段
	 */
	public int deleteByIdList(List<PK> idList) {
		if(idList == null || idList.isEmpty()){
			
		}
		return mapper.deleteByIdList(idList);
	}
	
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
