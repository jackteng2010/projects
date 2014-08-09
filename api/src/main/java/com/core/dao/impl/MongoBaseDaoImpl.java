package com.core.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.core.bean.BaseEntity;
import com.core.dao.IBaseDao;
import com.core.util.Exp;
import com.core.util.ExpBuilder;
import com.core.util.PageResult;

public class MongoBaseDaoImpl implements IBaseDao{

	protected static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private MongoOperations mongoTemplate;

	public MongoOperations getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoOperations mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public <T extends BaseEntity> void save(T t) {
		if(t.get_id() == null) {
			insert(t);
		} else {
			update(t);
		}
	}

	@Override
	public <T extends BaseEntity> void insert(T t) {
		if(t.get_id() != null){
			throw new RuntimeException("Warning: the object _id is valid when insert.");
		}
		Date d = new Date();
		t.setCreatedDate(d);
		t.setUpdatedDate(d);
		mongoTemplate.insert(t);
	}

	@Override
	public <T extends BaseEntity> void update(T t) {
		if(!ObjectId.isValid(t.get_id())){
			throw new RuntimeException("Warning: the object field _id is invalid when update.");
		}
		t.setUpdatedDate(new Date());
		mongoTemplate.save(t);
	}

	@Override
	public <T extends BaseEntity> void delete(T t) {
		mongoTemplate.remove(t);
	}

	@Override
	public <T> T findById(String id, Class<T> clazz) {
		return mongoTemplate.findById(id, clazz);
	}

	@Override
	public <T> T findOne(Exp exp, Class<T> clazz) {
		return mongoTemplate.findOne(ExpBuilder.getQuery(exp), clazz);
	}

	@Override
	public <T> List<T> list(Exp exp, Class<T> clazz) {
		return mongoTemplate.find(ExpBuilder.getQuery(exp), clazz);
	}

	@Override
	public <T> PageResult listPage(Exp exp, Class<T> clazz) {
        Exp p = getPageExp();
        if(exp == null) {
                 exp = p;
        } else {
                 exp = exp.andExp(p);//query;
        }
        if(! p.getSort().isEmpty()) exp.setSort(p.getSort());//sort
        exp.setLimit(p.getLimit());//limit
        exp.setLimitStart(p.getLimitStart());//limit
        BasicQuery query = ExpBuilder.getQuery(exp);
        int total = new Long(mongoTemplate.count(query, clazz)).intValue();
        List<T> data = mongoTemplate.find(query, clazz);
        PageResult re = new PageResult(query.getSkip(), query.getLimit(), total, data);
        return re;

	}

	@Override
	public <T> void delete(Exp exp, Class<T> clazz) {
		mongoTemplate.remove(ExpBuilder.getQuery(exp), clazz);
	}

	@Override
	public <T> void deleteById(String id, Class<T> clazz) {
		delete(new Exp("_id", id), clazz);
	}

	@Override
	public <T> int count(Exp exp, Class<T> clazz) {
		return new Long(mongoTemplate.count(ExpBuilder.getQuery(exp), clazz)).intValue();
	}

	@Override
	public <T> boolean exist(Exp exp, Class<T> clazz) {
		return count(exp, clazz) > 0;
	}
	
    public Exp getPageExp() {
    	Exp exp = new Exp();
    	ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(attrs == null) {
			return exp;
		}
		HttpServletRequest request = attrs.getRequest();
		if(request == null) {
			return exp;
		}
		//join query
		for(int i=0;;i++){
			String field = request.getParameter("filter[filters]["+i+"][field]");
			if(field == null) break;
			String operator = request.getParameter("filter[filters]["+i+"][operator]");
			String value = request.getParameter("filter[filters]["+i+"][value]");
            if("eq".equals(operator) || "lt".equals(operator) || "gt".equals(operator)){
            	exp = exp.andExp(field, com.core.util.EP.getEP(operator), Integer.valueOf(value));
            } else {
            	exp = exp.andExp(field, com.core.util.EP.getEP(operator), value);
            }
		}
		//join sort
		for(int i=0;;i++){
			String field = request.getParameter("sort["+i+"][field]");
			if(field == null) break;
			String dir = request.getParameter("sort["+i+"][dir]");
			if("asc".equals(dir)){
				exp.asc(field);
			}else{
				exp.desc(field);
			}
		}
		//join limit limitStart
		String skip = request.getParameter("skip");
		String pageSize = request.getParameter("pageSize");
		if(skip == null) {
			skip = (String)request.getAttribute("limitStart");
		}
		if(pageSize == null){
			pageSize = (String)request.getAttribute("limit");
		}
		//exp.setLimitStart(ApiUtil.getInteger(skip, 0));
		//exp.setLimit(ApiUtil.getInteger(pageSize, 0));
		return exp;
    }
}
