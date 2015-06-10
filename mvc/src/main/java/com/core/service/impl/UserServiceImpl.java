package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.bean.User;
import com.core.dao.ICommonDao;
import com.core.service.IUserService;
import com.core.util.EP;
import com.core.util.Exp;
import com.core.util.PageParams;
import com.core.util.PageResult;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private ICommonDao commonDao;

	@Override
	public User findUserByName(String userName) {
		Exp exp = new Exp("userName", EP.equals, userName);
		return commonDao.find(exp, User.class);
	}

	@Override
	public void createUser(User user) {
		commonDao.create(user);
	}

	@Override
	public void updateUser(User user) {
		commonDao.update(user);
	}

	@Override
	public void deleteUser(String id) {
		User user = commonDao.find(id, User.class);
		if(user != null) commonDao.delete(user);
	}

	@Override
	public PageResult<User> listUserPage(PageParams pageParams) {
		//return commonDao.listPage(null, User.class, null, pageParams);
		return null;
	}

}
