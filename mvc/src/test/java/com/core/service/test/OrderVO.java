package com.core.service.test;

public class OrderVO {

	private String name;
	private OrderItemVO orderItemVO;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public OrderItemVO getOrderItemVO() {
		return orderItemVO;
	}
	public void setOrderItemVO(OrderItemVO orderItemVO) {
		this.orderItemVO = orderItemVO;
	}
	
}
