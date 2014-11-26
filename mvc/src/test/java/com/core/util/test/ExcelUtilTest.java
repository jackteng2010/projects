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

import com.core.util.ExcelUtil;

public class ExcelUtilTest {

	/** 
	 * 测试所需的EXCEL模版文档，存放于webapp/template 
	 * 
	 * */
	
	@Test
	public void testReadFile() {
		List<List<Object>> list = ExcelUtil.readFile("C://demo01.xls");
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
		ExcelUtil.writeFile("C://Template01.xls", writeList);
		
		//read from excel
		List<List<Object>> readList = ExcelUtil.readFile("C://Template01.xls");
		
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
	
}
