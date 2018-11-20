package fabos.framework.core.orm.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Page<T> {

	/* 静态变量，用于设置结果是按照正序排列还是反序排列 */
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	/* 当前页码 */
	protected int pageNo = 1;
	/* 页面容量 */
	protected int pageSize = 1;

	/* orderBy表示通过哪个进行排序，比如说：id */
	protected String orderBy = null;

	/* order设置以哪种方式进行排序：可以是ASC也可以是DESC */
	protected String order = null;

	/* 是否自动计算 */
	protected boolean autoCount = true;

	/* 以下2个参数常作为 分页所需的"返回结果"! */
	// result表示当页面存在的实体类集合。
	protected List<T> result = Collections.emptyList();

	// totalCount表示总条数
	protected long totalCount = -1L;

	public Page() {
	}

	public Page(int pageSize) {
		this.setPageSize(pageSize);
	}

	public Page(int pageNo, int pageSize) {
		this.setPageSize(pageSize);
		this.setPageNo(pageNo);
	}

	public Page(int pageSize, boolean autoCount) {
		this.setPageSize(pageSize);
		this.setAutoCount(autoCount);
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize < 0) {
			this.pageSize = 1;
		}
	}

	/**
	 * 获得当前页面第一条数据的排列
	 * 
	 * @return
	 */
	public int getFirst() {
		return ((this.pageNo - 1) * this.pageSize) + 1;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(this.orderBy) && StringUtils.isNotBlank(this.order);
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		String[] var6 = orders;
		int var5 = orders.length;

		for (int var4 = 0; var4 < var5; ++var4) {
			String orderStr = var6[var4];
			if (!StringUtils.equals("desc", orderStr) && !StringUtils.equals("asc", orderStr)) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = StringUtils.lowerCase(order);
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取一共有多少页（设置的是总条数）
	 * 
	 * @return
	 */
	public long getTotalPages() {
		if (this.totalCount < 0L) {
			return -1L;
		} else {
			long count = this.totalCount / this.pageSize;
			if ((this.totalCount % this.pageSize) > 0L) {
				++count;
			}

			return count;
		}
	}

	/**
	 * 是否还有下一页
	 * 
	 * @return
	 */
	public boolean isHasNext() {
		return (this.pageNo + 1) <= this.getTotalPages();
	}

	/**
	 * 得到下一页的页码
	 * 
	 * @return
	 */
	public int getNextPage() {
		return this.isHasNext() ? this.pageNo + 1 : this.pageNo;
	}

	/**
	 * 是否有上一页
	 * 
	 * @return
	 */
	public boolean isHasPre() {
		return (this.pageNo - 1) >= 1;
	}

	/**
	 * 得到上一页页码
	 * 
	 * @return
	 */
	public int getPrePage() {
		return this.isHasPre() ? this.pageNo - 1 : this.pageNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
