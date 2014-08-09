package com.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class Exp {

	private String[] fields;
	private Map<String, Integer> sort = new LinkedHashMap<String, Integer>();
	private int limit;
	private int limitStart;
	
	private Object key;
	private EP operator;
	private Object value;
	
	public Exp() {
		super();
	}

	public Exp(Object key, Object value) {
		super();
		this.key = key;
		this.operator = EP.equals;
		this.value = value;
	}
	
	public Exp(Object key, EP operator, Object value) {
		super();
		this.key = key;
		this.operator = operator;
		this.value = value;
	}
	

	public Exp(Object key, EP operator, Object value, String[] fields, Map<String, Integer> sort, Integer limit, Integer limitStart) {
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
	public Exp andExp(Exp exp){
		if(exp != null){
			return new Exp(this, EP.and, exp, this.fields, this.sort, this.limit, this.limitStart);
		}
		return this;
	}

	public Exp orExp(Exp exp){
		if(exp != null){
			return new Exp(this, EP.or, exp, this.fields, this.sort, this.limit, this.limitStart);
		}
		return this;
	}
	
	public Exp andExp(Object key, Object value) {
		return andExp(new Exp(key, value));
	}

	public Exp orExp(Object key, Object value) {
		return orExp(new Exp(key, value));
	}
	
	public Exp andExp(Object key, EP operator, Object value) {
		return andExp(new Exp(key, operator, value));
	}

	public Exp orExp(Object key, EP operator, Object value) {
		return orExp(new Exp(key, operator, value));
	}
	//-------------------------------
	
	public void asc(String key){
		this.sort.put(key, 1);
	}
	
	public void desc(String key){
		this.sort.put(key, -1);
	}
	//-------------------------------

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public Map<String, Integer> getSort() {
		return sort;
	}

	public void setSort(Map<String, Integer> sort) {
		this.sort = sort;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
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
