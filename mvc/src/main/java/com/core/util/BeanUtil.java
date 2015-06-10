package com.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import com.google.gson.Gson;
public final class BeanUtil {

	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);



/*    public static <T> T copyToObject(Object entity, Class<T> destinationClass) {
        if (entity == null) {
            return null;
        }
        T t;
        try {
            t = destinationClass.newInstance();
            BeanUtils.copyProperties(t, SqlUtil.invokeDate(entity));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("copyToObject{} exception is: " + e.getMessage());
            throw new CopyPropertiesException();
        }
        return t;
    }*/
    
    public static List<Map<String,Object>> transBean2List(List<Object> list){
        if(list == null) return null;
        List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
        for(Object obj : list){
            listMap.add(transBean2Map(obj));
        }
        return listMap;
    }
    
    public static Map<String, Object> transBean2Map(Object obj) {  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                // 过滤class属性  
                if (!key.equals("class")) {// 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    map.put(key, value);  
                }  
            }  
        } catch (Exception e) {  
            logger.error("transBean2Map Error " + e);  
        }  
        return map;  
    }  
  
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param source
     * @param target
     * @throws BeansException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (SecurityException e) {
                        throw new FatalBeanException("Could not copy properties from source to target", e);
                    } catch (IllegalAccessException e) {
                        throw new FatalBeanException("Could not copy properties from source to target", e);
                    } catch (IllegalArgumentException e) {
                        throw new FatalBeanException("Could not copy properties from source to target", e);
                    } catch (InvocationTargetException e) {
                        throw new FatalBeanException("Could not copy properties from source to target", e);
                    }
                }
            }
        }
    }
    
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param source
     * @param target
     * @throws BeansException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void copyProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : new ArrayList<String>();
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
            	if(ignoreList.contains(targetPd.getName())){
            		continue;
            	}
                PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (SecurityException e) {
                        throw new FatalBeanException("Could not copy properties from source to target", e);
                    } catch (IllegalAccessException e) {
                        throw new FatalBeanException("Could not copy properties from source to target", e);
                    } catch (IllegalArgumentException e) {
                        throw new FatalBeanException("Could not copy properties from source to target", e);
                    } catch (InvocationTargetException e) {
                        throw new FatalBeanException("Could not copy properties from source to target", e);
                    }
                }
            }
        }
    }
}
