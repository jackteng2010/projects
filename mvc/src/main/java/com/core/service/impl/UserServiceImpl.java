package com.core.service.impl;

import org.springframework.stereotype.Service;

import com.core.bean.User;
import com.core.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Override
	public User findUserByName(String name) {
		User user = new User();
		user.setId("112233445566");
		user.setUserName(name);
		user.setPassword("123456");
		return user;
	}

}
