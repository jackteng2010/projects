package com.core.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageResult<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int pageSize = 10;
	private long start;
	private List<T> data;
	private long totalCount;
	
	public PageResult() {
		this(10, 0, new ArrayList<T>(), 0);
	}
	
	public PageResult(int pageSize, long start, List<T> data, long totalCount) {
		super();
		this.pageSize = pageSize;
		this.start = start;
		this.data = data;
		this.totalCount = totalCount;
	}

	/**获取总页数*/
	public long getTotalPageCount(){
		if(totalCount % pageSize == 0){
			return totalCount/pageSize;
		} else {
			return totalCount/pageSize +1;
		}
	}

	/**获取当前页码*/
	public long getCurrentPageNo(){
		return start/pageSize + 1;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getStart() {
		return start;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setStart(long start) {
		this.start = start;
	}
	
}
