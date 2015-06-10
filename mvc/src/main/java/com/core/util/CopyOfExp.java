package com.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CopyOfExp {

	private String[] fields;
	private Map<String, Boolean> sort = new LinkedHashMap<String, Boolean>();
	private Integer limit;
	private Integer limitStart;
	
	private Object key;
	private EP operator;
	private Object value;
	
	public CopyOfExp() {
		super();
	}

	public CopyOfExp(Object key, Object value) {
		super();
		this.key = key;
		this.operator = EP.equals;
		this.value = value;
	}
	
	public CopyOfExp(Object key, EP operator, Object value) {
		super();
		this.key = key;
		this.operator = operator;
		this.value = value;
	}
	

	public CopyOfExp(Object key, EP operator, Object value, String[] fields, Map<String, Boolean> sort, Integer limit, Integer limitStart) {
		super();
		this.fields = fields;
		this.sort = sort;
		this.limit = limit;
		this.limitStart = limitStart;
		this.key = key;
		this.operator = operator;
		this.value = value;
	}

	//-------------------------------
	public CopyOfExp andExp(CopyOfExp exp){
		if(exp != null){
			return new CopyOfExp(this, EP.and, exp, this.fields, this.sort, this.limit, this.limitStart);
		}
		return this;
	}

	public CopyOfExp orExp(CopyOfExp exp){
		if(exp != null){
			return new CopyOfExp(this, EP.or, exp, this.fields, this.sort, this.limit, this.limitStart);
		}
		return this;
	}
	
	public CopyOfExp andExp(Object key, Object value) {
		return andExp(new CopyOfExp(key, value));
	}

	public CopyOfExp orExp(Object key, Object value) {
		return orExp(new CopyOfExp(key, value));
	}
	
	public CopyOfExp andExp(Object key, EP operator, Object value) {
		return andExp(new CopyOfExp(key, operator, value));
	}

	public CopyOfExp orExp(Object key, EP operator, Object value) {
		return orExp(new CopyOfExp(key, operator, value));
	}
	//-------------------------------
	
	public void asc(String key){
		this.sort.put(key, true);
	}
	
	public void desc(String key){
		this.sort.put(key, false);
	}
	//-------------------------------

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public Map<String, Boolean> getSort() {
		return sort;
	}

	public void setSort(Map<String, Boolean> sort) {
		this.sort = sort;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public EP getOperator() {
		return operator;
	}

	public void setOperator(EP operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
