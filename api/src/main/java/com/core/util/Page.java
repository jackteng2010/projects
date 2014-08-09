package com.core.util;

import java.io.Serializable;

public class Page implements Serializable{

	private static final long serialVersionUID = 1L;
	private int limitStart;
	private int limit;	//just pageSize
	private int total;
	private int totalPages;
	
	public Page() {
		this(0, 10, 0);
	}

	public Page(int limitStart, int limit, int total) {
		this.limitStart = limitStart;
		this.limit = limit==0 ? 10 : limit;
		this.total = total;
		if(this.total%this.limit == 0){
			this.totalPages = this.total/this.limit;
		} else {
			this.totalPages = this.total/this.limit + 1;
		}
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
}
