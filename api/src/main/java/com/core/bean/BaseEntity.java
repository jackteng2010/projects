package com.core.bean;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected String _id;	
	protected Date createdDate;
	protected Date updatedDate;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
