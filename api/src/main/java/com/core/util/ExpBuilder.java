package com.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.BasicQuery;
import com.core.util.EP;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public class ExpBuilder {
	
	public static BasicQuery getQuery(Exp exp){
		DBObject queryObject = new BasicDBObject();
		DBObject sortObject = new BasicDBObject();
		DBObject fieldsObject = null;
		int limit = 0;
		int limitStart = 0;
		if(exp != null){
			queryObject = build(exp);
			sortObject = new BasicDBObject(exp.getSort());
			if(exp.getFields() != null){
				fieldsObject = new BasicDBObject();
				for(String k : exp.getFields()){
					fieldsObject.put(k, 1);
				}
			}
			limit = exp.getLimit();
			limitStart = exp.getLimitStart();
		}
		BasicQuery query = new BasicQuery(queryObject, fieldsObject);
		query.setSortObject(sortObject);
		query.limit(limit);
		query.skip(limitStart);
		return query;
	}
	
	
    public static DBObject build(Exp exp) {
    	QueryBuilder query = new QueryBuilder();
    	if(exp == null || exp.getKey() == null) {
    		return query.get();
    	}
    	Object key = exp.getKey();
        Object value = exp.getValue();
        EP ep = exp.getOperator();
        
    	if("_id".equals(key)){
    		if(value instanceof String){
    			String id = String.valueOf(value);
    			if(ObjectId.isValid(id)){
    				value = new ObjectId(id);
    			} else {
    				value = id;
    			}
    		} else if(value instanceof String[]){
    			String[] strs = (String[])value;
    			List<Object> ids = new ArrayList<Object>();
    			for(String id : strs){
        			if(ObjectId.isValid(id)){
        				ids.add(new ObjectId(id));
        			} else {
        				ids.add(id);
        			}
    			}
    			value = ids;
    		} else if(value instanceof List){
    			List list = (List)value;
    			List<ObjectId> ids = new ArrayList<ObjectId>();
    			for(Object item : list){
    				String id = String.valueOf(item);
        			if(ObjectId.isValid(id)){
        				ids.add(new ObjectId(id));
        			} else {
        				ids.add(new ObjectId());
        			}
    			}
    			value = ids;
    		}else {
    			//do nothing
    		}
    	}
        switch(ep){
        case and:
        	query.and(build((Exp)key));
        	query.and(build((Exp)value));
        	break;
        case or:
        	query.or(build((Exp)key));
        	query.or(build((Exp)value));
        	break;
        case is:
        	if(value != null){
        		query.put(key.toString());
        		query.is(value);
        	}
        	break;
        case equals:
        	query.put(key.toString());
        	query.is(value);
        	break;
        case like:
        	query.put(key.toString());
        	query.regex(Pattern.compile(value.toString(), Pattern.CASE_INSENSITIVE));
        	break;
        case notEquals:
        	query.put(key.toString());
        	query.notEquals(value);
        	break;
        case greater:
        	query.put(key.toString());
        	query.greaterThan(value);
        	break;
        case greaterQquals:
        	query.put(key.toString());
        	query.greaterThanEquals(value);
        	break;
        case less:
        	query.put(key.toString());
        	query.lessThan(value);
        	break;
        case lessQquals:
        	query.put(key.toString());
        	query.lessThanEquals(value);
        	break;
        case in:
        	query.put(key.toString());
        	query.in(value);
        	break;
        case notIn:
        	query.put(key.toString());
        	query.notIn(value);
        	break;
        default:
        	System.out.println("Error: Exp operator not exist.");
        	break;
        }
        return query.get();
    }
}