package com.core.service.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.core.util.BeanUtil;

public class CommonTest {

	@Test
	public void test() {
		OrderVO order = new OrderVO();
		order.setName("myOrder");
		OrderItemVO item = new OrderItemVO();
		item.setB1("b11111");
		item.setV2(222222L);
		order.setOrderItemVO(item);
		
		Map<String,Object> aa = new HashMap<String,Object>();
		aa.put("test", order);
		
		String json = new com.google.gson.Gson().toJson(aa);
		
		Map<String,Object> params = new com.google.gson.Gson().fromJson(json, Map.class);
		
		
		System.out.println(params);
		
	}

}
