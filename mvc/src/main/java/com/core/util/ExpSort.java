package com.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**排序ASC DESC*/
public class ExpSort {

	private Map<String,Boolean> sort = new LinkedHashMap<String,Boolean>();
	
	public ExpSort() {
		super();
	}

	public ExpSort(Map<String, Boolean> sort) {
		super();
		this.sort = sort;
	}

	/**Method*/
	public ExpSort(String propertyName, boolean asc) {
		sort.put(propertyName, asc);
	}
	
	public ExpSort and(ExpSort expSort){
		ExpSort newExpSort = new ExpSort();
		newExpSort.getSort().putAll(this.getSort());
		newExpSort.getSort().putAll(expSort.getSort());
		return newExpSort;
	}

	public Map<String, Boolean> getSort() {
		return sort;
	}

	public void setSort(Map<String, Boolean> sort) {
		this.sort = sort;
	}
	
}
