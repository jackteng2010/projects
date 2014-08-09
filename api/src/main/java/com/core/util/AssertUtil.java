package com.core.util;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.bson.types.ObjectId;

import com.core.bean.BaseEntity;
import com.core.dao.IBaseDao;
import com.core.util.Exp;

public class AssertUtil {

	private static IBaseDao baseDao;
	
	public static <T> void isExist(Exp exp, Class<T> clazz, String message){
		if(! baseDao.exist(exp, clazz)){
			throw new IllegalArgumentException(message);
		}
	}
	
	public static <T> void isExist(String key, Object value, Class<T> clazz, String message){
		if(! baseDao.exist(new Exp(key, value), clazz)){
			throw new IllegalArgumentException(message);
		}
	}
	
	public static <T> void notExist(String key, Object value, Class<T> clazz){
		notExist(key, value, clazz, "[Assertion failed] -the object argument has exist in db.");
	}
	
	public static <T> void notExist(String key, Object value, Class<T> clazz, String message){
		if(baseDao.exist(new Exp(key, value), clazz)){
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void notAfter(String date1, String date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2);
			notAfter(d1, d2, date1 + " should not be after " + date2);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(date1 + " should not be after " + date2);
		}
	}
	
	public static void notAfter(Date date1, Date date2){
		notAfter(date1, date2, "[Assertion failed] - " + date1 + " should not be after " + date2);
	}
	
	public static void notAfter(Date date1, Date date2, String message){
		if(date1.after(date2)){
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isURL(String str){
		isURL(str, "[Assertion failed] - " + str + " is not a url.");
	}
	
	public static void isURL(String str, String message){
		notEmpty(str);
		try{
			new URL(str);
		} catch(Exception e){
			throw new IllegalArgumentException(message);
		}
	}
	
	
	public static void inValues(Object str, Object[] values){
		inValues(str, values, "[Assertion failed] - " + str + " is not in " + Arrays.toString(values));
	}
	
	public static void inValues(Object str, Object[] values, String message){
		notEmpty(values);
		if(! Arrays.asList(values).contains(str)){
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void notEmpty(String[] values){
		notEmpty(values, "[Assertion failed] -this collection must not be empty.");
	}
	
	public static void notEmpty(String[] values, String message){
		if(values == null || values.length ==0){
			throw new IllegalArgumentException(message);
		}
	}
	
	public static <T> void isObjectId(String id){
		isObjectId(id, "[Assertion failed] -the _id argument is invalid.");
	}
	
	public static <T> void isObjectId(String id, String message){
		if(! ObjectId.isValid(id)){
			throw new IllegalArgumentException(message);
		}
	}

	public static <T extends BaseEntity> void isExist(T t){
		notNull(t);
		isObjectId(t.get_id());
		isExist("_id", t.get_id(), t.getClass());
	}
	
	public static <T> void isExist(Exp exp, Class<T> clazz){
		isExist(exp, clazz, "[Assertion failed] -the object do not exist in db.");
	}
	
	public static <T> void isExist(String key,Object value, Class<T> clazz){
		isExist(key, value, clazz, "[Assertion failed] -the object argument do not exist in db.");
	}
	
	public static void notEmpty(String collection, String message) {
		if (collection == null || collection.trim().isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(String collection) {
		notEmpty(collection, "[Assertion failed] - this argument must not be empty.");
	}
	
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	public static void notEmpty(Object[] array, String message) {
		if (array == null ||array.length == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	@SuppressWarnings("rawtypes")
	public static void notEmpty(Collection collection, String message) {
		if (collection == null || collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void notEmpty(Collection collection) {
		notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	@SuppressWarnings("rawtypes")
	public static void notEmpty(Map map, String message) {
		if (map == null || map.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void notEmpty(Map map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	public static IBaseDao getBaseDao() {
		return baseDao;
	}

	public static void setBaseDao(IBaseDao baseDao) {
		AssertUtil.baseDao = baseDao;
	}

}