package com.core.bean;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String PHONE = "phone";
	public static final String REGISTER_DATE = "regDate";
	
	private String userName;
	private String password;
	private String email;
	private String phone;
	private String regDate;
	private boolean superAdmin;//是否是超级管理员
	private List<String> roles = new ArrayList<String>();//角色集合
	private long[] rightSum;//用户权限总和

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public long[] getRightSum() {
		return rightSum;
	}

	public void setRightSum(long[] rightSum) {
		this.rightSum = rightSum;
	}
}
