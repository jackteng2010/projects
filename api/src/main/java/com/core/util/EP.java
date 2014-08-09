package com.core.util;

/**
 * is : "=" , will ignore null value.
 * 
 * */

public enum EP {
	
	is,and,or,equals,like,greater,less,greaterQquals,lessQquals,in,notIn,notEquals;
	
	public static EP getEP(String ep){
		if(is.name().equals(ep)){
			return is;
		} else if(and.name().equals(ep)){
			return and;
		} else if(or.name().equals(ep)){
			return or;
		} else if(equals.name().equals(ep) || "eq".equals(ep)){
			return equals;
		} else if(like.name().equals(ep)){
			return like;
		} else if(greater.name().equals(ep) || "gt".equals(ep)){
			return greater;
		} else if(less.name().equals(ep) || "lt".equals(ep)){
			return less;
		} else if(greaterQquals.name().equals(ep)){
			return greaterQquals;
		} else if(lessQquals.name().equals(ep)){
			return lessQquals;
		} else if(in.name().equals(ep) || "contains".equals(ep)){
			return in;
		} else if(notIn.name().equals(ep)){
			return notIn;
		} else if(notEquals.name().equals(ep) || "neq".equals(ep)){
			return notEquals;
		}
		return null;
	}
}
