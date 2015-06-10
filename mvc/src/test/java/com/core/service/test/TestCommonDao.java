package com.core.service.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.core.bean.User;
import com.core.dao.ICommonDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/resources/spring/spring-context.xml")
public class TestCommonDao {
	
	@Autowired
	private ICommonDao commonDao;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Test
	public void test() {
		System.out.println("======================Begin=====================");
		List list = hibernateTemplate.find("from User");
		Object obj = list.get(0);
		System.out.println(obj.getClass());
		System.out.println("======================End=====================");
		
	}
	
	
	

}
