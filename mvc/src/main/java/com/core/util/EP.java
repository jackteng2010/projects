package com.core.util;

public enum EP {
	
	is,and,or,equals,like,greater,less,greaterQquals,lessQquals,between,in,notIn,notEquals;
	
	public static EP getEP(String ep){
		EP vv = null;
		if(is.name().equalsIgnoreCase(ep)){
			vv = is;
		} else if(and.name().equalsIgnoreCase(ep)){
			vv =  and;
		} else if(or.name().equalsIgnoreCase(ep)){
			vv =  or;
		} else if(equals.name().equalsIgnoreCase(ep)){
			vv =  equals;
		} else if(like.name().equalsIgnoreCase(ep)){
			vv =  like;
		} else if(between.name().equalsIgnoreCase(ep)){
			vv =  between;
		}  else if(greater.name().equalsIgnoreCase(ep)){
			vv =  greater;
		} else if(less.name().equalsIgnoreCase(ep)){
			vv =  less;
		} else if(greaterQquals.name().equalsIgnoreCase(ep)){
			vv =  greaterQquals;
		} else if(lessQquals.name().equalsIgnoreCase(ep)){
			vv =  lessQquals;
		} else if(in.name().equalsIgnoreCase(ep)){
			vv =  in;
		} else if(notIn.name().equalsIgnoreCase(ep)){
			vv =  notIn;
		} else if(notEquals.name().equalsIgnoreCase(ep)){
			vv =  notEquals;
		} else {
			//do nothing
		}
		return vv;
	}
}
