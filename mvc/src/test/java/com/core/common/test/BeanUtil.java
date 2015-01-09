/*
 * Copyright (C), 2013-2014, 上汽集团
 * FileName: BeanUtilss.java
 * Author:   v_liubanggen
 * Date:     2014年8月1日 下午1:08:13
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.core.common.test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;


/**
 * 〈一句话功能简述〉<br>
 * BeanUtil
 * 
 * @author v_liubanggen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BeanUtil extends org.springframework.beans.BeanUtils {
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
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
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
