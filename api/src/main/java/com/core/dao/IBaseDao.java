package com.core.dao;

import java.util.List;

import com.core.bean.BaseEntity;
import com.core.util.Exp;
import com.core.util.PageResult;

public interface IBaseDao {
	
	public <T extends BaseEntity> void save(T t);
	
	public <T extends BaseEntity> void insert(T t);
	
	public <T extends BaseEntity> void update(T t);
	
	public <T extends BaseEntity> void delete(T t);

	public <T> T findById(String id, Class<T> clazz);
	
	public <T> T findOne(Exp exp, Class<T> clazz);
	
	public <T> List<T> list(Exp exp, Class<T> clazz);
	
	public <T> PageResult listPage(Exp exp, Class<T> clazz);
	
	public <T> void delete(Exp exp, Class<T> clazz);
	
	public <T> void deleteById(String id, Class<T> clazz);
	
	public <T> int count(Exp exp, Class<T> clazz);
	
	public <T> boolean exist(Exp exp, Class<T> clazz);
	
}