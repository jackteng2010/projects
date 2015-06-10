package com.core.service;

import com.core.bean.User;
import com.core.util.PageParams;
import com.core.util.PageResult;

public interface IUserService {

	public User findUserByName(String userName);
	
	public void createUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUser(String id);
	
	public PageResult<User> listUserPage(PageParams pageParams);
	
	
}
