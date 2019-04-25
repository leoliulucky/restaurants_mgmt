package com.benxiaopao.common.util;


/**
 * 分页工具公共类
 * 
 * @author liupoyang
 * @since 2019-04-04
 */
public class Pagination {
	/**
	 * 当前页码
	 */
	private int pageNum;
	/**
	 * 页面大小
	 */
	private int pageSize;
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 总页数
	 */
	private int pageCount;
	
	/**
	 * 构造方法
	 * 
	 * @param pageNum 当前页码
	 * @param pageSize 页面大小
	 */
	public Pagination(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	
	/**
	 * 获取当前分页对象
	 * @param pageNum 页码
	 * @param pageSize 页面大小
	 * @return Pagination
	 */
	public static Pagination currentPagination(int pageNum, int pageSize) {
		Pagination pagination = ThreadContent.pagination();
		//创建分页对象
		if (pagination == null) {
			pagination = new Pagination(pageNum, pageSize);
			ThreadContent.pagination(pagination);
		}
		return pagination;
	}
	
	/**
	 * 当前页码Get方法
	 * @return
	 */
	public int getPageNum() {
		return pageNum;
	}
	
	/**
	 * 页面大小Get方法
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 总记录数Get方法
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 总记录数Set方法
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if(totalCount % pageSize > 0 || totalCount == 0){
			this.pageCount = (totalCount / pageSize) + 1;
		}else{
			this.pageCount = (totalCount / pageSize);
		}
	}

	/**
	 * 计算总页数Get方法
	 * @return
	 */
	public int getPageCount() {
		/*if(totalCount % pageSize > 0){
			return (totalCount / pageSize) + 1;
		}
		return (totalCount / pageSize);*/
		return pageCount;
	}
	
	
	/**
	 * 计算分页查询开始位置方法
	 * @return
	 */
	public int start() {
		return (pageNum - 1) * pageSize;
	}

}
