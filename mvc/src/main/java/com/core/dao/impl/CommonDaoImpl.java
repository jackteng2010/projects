package com.core.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.core.dao.ICommonDao;
import com.core.util.Exp;
import com.core.util.ExpBuilder;
import com.core.util.PageParams;
import com.core.util.PageResult;

/**	
 * 采用HIBERNATE实现数据库操作，已通过XML BEAN注册
 */
public class CommonDaoImpl implements ICommonDao {

	//XML BEAN INJECTION
	private HibernateTemplate hibernateTemplate;

	//-------------------------------------------
	// 基础方法 CRUD
	
	@Override
	public void create(Object entity) {
		hibernateTemplate.persist(entity);
	}

	@Override
	public void update(Object entity) {
		hibernateTemplate.update(entity);
	}

	@Override
	public void delete(Object entity) {
		hibernateTemplate.delete(entity);
	}

	@Override
	public <T> T find(Serializable id, Class<T> entityClass) {
		return hibernateTemplate.load(entityClass, id);
	}
	
	@Override
	public <T> List<T> listAll(Class<T> entityClass) {
		return list(new Exp(), entityClass);
	}
	

	//---------------------------------------------------------
	//---------Exp 工具类查询
	
	@Override
	public <T> T find(Exp exp, Class<T> entityClass) {
		List<T> list = list(exp, entityClass);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**基础*/
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> list(Exp exp, Class<T> entityClass) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		if(exp != null){
			Criterion criterion = ExpBuilder.buildCriterion(exp);
			if(criterion != null){
				detachedCriteria.add(criterion);
			}
			for(Entry<String, Boolean> entry : exp.getSortMap().entrySet()){
				if(entry.getValue()){
					detachedCriteria.addOrder(Order.asc(entry.getKey()));
				} else {
					detachedCriteria.addOrder(Order.desc(entry.getKey()));
				}
			}
		}
		int first = exp.getLimitStart()==null? -1 : exp.getLimitStart();
		int limit = exp.getLimit()==null? -1 : exp.getLimit();
		return hibernateTemplate.findByCriteria(detachedCriteria, first, limit);
	}

	@Override
	public <T> long count(Exp exp, Class<T> entityClass) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		detachedCriteria.add(ExpBuilder.buildCriterion(exp));
		detachedCriteria.setProjection(Projections.rowCount());
		return ((Long) hibernateTemplate.findByCriteria(detachedCriteria).get(0)).longValue(); 
	}

	@Override
	public <T> PageResult<T> listPage(Exp exp, Class<T> entityClass) {
		int first = exp.getLimitStart()==null? -1 : exp.getLimitStart();
		int limit = exp.getLimit()==null? -1 : exp.getLimit();
		PageResult<T> page = new PageResult<T>();
		page.setStart(first);
		page.setPageSize(limit);
		page.setTotalCount(count(exp, entityClass));
		page.setData(list(exp, entityClass));
		return page;
	}

	//---------------------------------------------------------
	//---------VO 转换查询
	
	@Override
	public <T> T find(Exp exp, Class<?> entityClass, Class<T> requireType) {
		List<T> list = list(exp, entityClass, requireType);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**基础*/
	@Override
	public <T> List<T> list(Exp exp, Class<?> entityClass, Class<T> requireType) {
		List<?> list = list(exp, entityClass);
		List<T> returnList = new ArrayList<T>();
		if(list != null){
			for(Object item : list){
				returnList.add(copyProperties(item, requireType));
			}
		}
		return returnList;
	}

	@Override
	public <T> PageResult<T> listPage(Exp exp, Class<?> entityClass, Class<T> requireType) {
		int first = exp.getLimitStart()==null? -1 : exp.getLimitStart();
		int limit = exp.getLimit()==null? -1 : exp.getLimit();
		PageResult<T> page = new PageResult<T>();
		page.setStart(first);
		page.setPageSize(limit);
		page.setTotalCount(count(exp, entityClass));
		page.setData(list(exp, entityClass, requireType));
		return page;
	}

	
	/**转换source为requireType对象*/
	public <T> T copyProperties(Object source, Class<T> requireType){
		T target = null;
		if(source != null){
			target = org.springframework.beans.BeanUtils.instantiateClass(requireType);
			org.springframework.beans.BeanUtils.copyProperties(source, target);
		}
		return target;
	}

	
	//---------------------------------------------------------
	//---------HQL 查询
	
	@Override
	public <T> T find(String sqlName, Class<T> requireType) {
		System.out.println("----------`12-------------");
		return null;
	}
	
	/**基础*/
	@Override
	public <T> List<T> list(String sqlName, Class<T> requireType) {
		System.out.println("----------`12-------------");
		return null;
	}

	@Override
	public <T> long count(String sqlName, Class<T> requireType) {
		System.out.println("----------`12-------------");
		return 0;
	}

	@Override
	public <T> PageResult<T> listPage(String sqlName, Class<T> requireType, PageParams pageParams) {
		System.out.println("----------`12-------------");
		return null;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
}
