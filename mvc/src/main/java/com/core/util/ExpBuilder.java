package com.core.util;

import java.util.Arrays;
import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class ExpBuilder {

	public static Criterion buildCriterion(Exp exp){
		if(exp ==null || exp.getKey() == null) {
			return null;
		}
		Object key = exp.getKey();
		EP op = exp.getOperator();
		Object value = exp.getValue();
		
		Criterion criterion = null;
		switch(op){
		case is:
			criterion = Restrictions.eq(key.toString(), value);
			break;
		case equals:
			criterion = Restrictions.eq(key.toString(), value);
			break;
		case like:
			criterion = Restrictions.like(key.toString(), value);
			break;
		case greater:
			criterion = Restrictions.gt(key.toString(), value);
			break;
		case less:
			criterion = Restrictions.lt(key.toString(), value);
			break;
		case greaterQquals:
			criterion = Restrictions.ge(key.toString(), value);
			break;
		case lessQquals:
			criterion = Restrictions.le(key.toString(), value);
			break;
		case between:
			Object[] objs = (Object[])value;
			criterion = Restrictions.between(key.toString(), objs[0], objs[1]);
			break;
		case in:
			if(value instanceof Collection){
				criterion = Restrictions.in(key.toString(), (Collection<?>)value);
			} else if(value instanceof Arrays) {
				criterion = Restrictions.in(key.toString(), (Object[])value);
			}
			break;
		case notIn:
			if(value instanceof Collection){
				criterion = Restrictions.not(Restrictions.in(key.toString(), (Collection<?>)value));
			} else if(value instanceof Arrays) {
				criterion = Restrictions.not(Restrictions.in(key.toString(), (Object[])value));
			}
			break;
		case notEquals:
			criterion = Restrictions.ne(key.toString(), value);
			break;
		case and:
			criterion = Restrictions.and(buildCriterion((Exp)key), buildCriterion((Exp)value));
			break;
		case or:
			criterion = Restrictions.or(buildCriterion((Exp)key), buildCriterion((Exp)value));
			break;
		default:
			//do nothing
			break;
		}
		return criterion;
	}
	
}
