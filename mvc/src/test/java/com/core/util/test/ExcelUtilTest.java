package com.core.util.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import com.core.bean.User;
import com.core.util.ExcelUtil;

public class ExcelUtilTest {

	@Test
	public void test001(){
		User user01 = new User();
		user01.setUserName("1111");
		User user02 = new User();
		user02.setUserName("2222");
		user02.setPassword("12346");
		System.out.println(user02.getPassword());
		org.springframework.beans.BeanUtils.copyProperties(user01, user02);
		System.out.println(user02.getPassword());
	}
	
	/** 
	 * 测试所需的EXCEL模版文档，存放于webapp/template 
	 * 
	 * */
	
	@Test
	public void testReadFile() {
		List<List<Object>> list = ExcelUtil.read("C://demo01.xls");
		System.out.println(list.toString());
	}
	
	@Test
	public void testWriteFile() throws FileNotFoundException, ParseException {
		List<List<Object>> writeList = new ArrayList<List<Object>>();
		List<Object> subList01 = new ArrayList<Object>();
		subList01.add("小猪");
		subList01.add(20);
		subList01.add(new Date());

		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd"); 
		Date d2 = sb.parse("2014-10-10");
		
		List<Object> subList02 = new ArrayList<Object>();
		subList02.add("小名");
		subList02.add(30);
		subList02.add(d2);
		writeList.add(subList01);
		writeList.add(subList02);
		
		//save into excel
		ExcelUtil.write(writeList, "C://Template01.xls");
		
		//read from excel
		List<List<Object>> readList = ExcelUtil.read("C://Template01.xls");
		
		//compare list01 list02
		for(int i=0; i < writeList.size(); i ++){
			List<Object> wList = writeList.get(i);
			List<Object> rList = readList.get(i);
			for(int j=0; j < wList.size(); j++){
				//compare each field
				if(! wList.get(j).equals(rList.get(j))){
					fail("写入功能错误，请确认--");
				}
			}
		}
		System.out.println("写入测试成功！！！！");
	}
	
	@Test
	public void testOne(){
		File file = new File("");
		file.getAbsolutePath();
		
	}
}
