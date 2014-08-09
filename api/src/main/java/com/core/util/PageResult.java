package com.core.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageResult implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Page pagination;
	private int total;
	private List data;

	public PageResult() {
		this(0,10,0,new ArrayList());
	}
	
	public PageResult(int limitStart, int limit, int total, List data) {
		pagination = new Page(limitStart, limit, total);
		this.total = total;
		this.data = data;
	}

	public Map<String,Object> toMap(){
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("total", this.total);
		map.put("pagination", this.pagination);
		map.put("data", this.data);
		return map;
	}
	
	public Page getPagination() {
		return pagination;
	}

	public void setPagination(Page pagination) {
		this.pagination = pagination;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
}
