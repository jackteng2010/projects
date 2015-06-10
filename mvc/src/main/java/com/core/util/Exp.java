package com.core.util;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Exp implements Serializable {

	private static final long serialVersionUID = 3889130675973954781L;
	
	private Object key;
	private EP operator;
	private Object value;
	
	private Integer limit;
	private Integer limitStart;
	private Map<String,Boolean> sortMap = new LinkedHashMap<String,Boolean>();
	
	/**Constructor*/
	public Exp() {
		super();
	}
	
	public Exp(Object key, EP operator, Object value) {
		super();
		this.key = key;
		this.operator = operator;
		this.value = value;
	}

	/**Method*/
	public Exp andExp(Exp exp){
		if(exp != null){
			return new Exp(this, EP.and, exp);
		}
		return this;
	}

	public Exp orExp(Exp exp){
		if(exp != null){
			return new Exp(this, EP.or, exp);
		}
		return this;
	}
	
	public Exp andExp(Object key, EP operator, Object value) {
		return andExp(new Exp(key, operator, value));
	}

	public Exp orExp(Object key, EP operator, Object value) {
		return orExp(new Exp(key, operator, value));
	}

	/**Get, Set*/
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

	/**asc:true desc:false*/
	public Map<String, Boolean> getSortMap() {
		return sortMap;
	}

	public void setSortMap(Map<String, Boolean> sortMap) {
		this.sortMap = sortMap;
	}
	
}
