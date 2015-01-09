package com.core.common.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.core.bean.User;

public class MainTest {

	@Test
	public void test() throws IllegalAccessException, InvocationTargetException {
		User user1 = new User(null, "Jack001", null, null);
		User user2 = new User("222", "Jack002", null, null);
		com.core.common.test.BeanUtil.copyProperties(user1, user2);
		System.out.println("com.core.common.test.BeanUtil " + new com.google.gson.Gson().toJson(user2));
				
		user1 = new User(null, "Jack001", null, null);
		user2 = new User("222", "Jack002", null, null);
		org.apache.commons.beanutils.BeanUtils.copyProperties(user1, user2);
		System.out.println("org.apache.commons.beanutils.BeanUtils " + new com.google.gson.Gson().toJson(user2));
		
		user1 = new User(null, "Jack001", null, null);
		user2 = new User("222", "Jack002", null, null);
		org.springframework.beans.BeanUtils.copyProperties(user1, user2,new String[]{"id"});
		System.out.println("org.springframework.beans.BeanUtils " + new com.google.gson.Gson().toJson(user2));
	}

}
