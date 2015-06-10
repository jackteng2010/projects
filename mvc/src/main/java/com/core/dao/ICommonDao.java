package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.util.Exp;
import com.core.util.PageParams;
import com.core.util.PageResult;

/**定义 DAO层基本接口*/
public interface ICommonDao {
	
	//-------------------------------------------
	// 基础方法 CRUD
	public void create(Object entity);
	
	public void update(Object entity);
	
	public void delete(Object entity);
	
	public <T> T find(Serializable id, Class<T> entityClass);
	
	public <T> List<T> listAll(Class<T> entityClass);
	
	//---------------------------------------------------------
	//---------EXP 工具类查询
	public <T> T find(Exp exp, Class<T> entityClass);
	
	public <T> List<T> list(Exp exp, Class<T> entityClass);
	
	public <T> long count(Exp exp, Class<T> entityClass);
	
	public <T> PageResult<T> listPage(Exp exp, Class<T> entityClass);
	
	//---------------------------------------------------------
	//---------EXP VO 转换查询
	public <T> T find(Exp exp, Class<?> entityClass, Class<T> requireType);
	
	public <T> List<T> list(Exp exp, Class<?> entityClass, Class<T> requireType);
	
	public <T> PageResult<T> listPage(Exp exp, Class<?> entityClass, Class<T> requireType);
	
	//---------------------------------------------------------
	//---------HQL 查询
	public <T> T find(String sqlName, Class<T> entityClass);
	
	public <T> List<T> list(String sqlName, Class<T> entityClass);
	
	public <T> long count(String sqlName, Class<T> entityClass);
	
	public <T> PageResult<T> listPage(String sqlName, Class<T> entityClass, PageParams pageParams);
	
	
}
