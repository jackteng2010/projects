package com.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.core.bean.Authority;
import com.core.service.IAuthorityService;
import com.core.service.IUserService;

/**
 * 登录用户信息查询类 
 */
public class MyUserDetailService implements UserDetailsService {
	
	private IUserService userService;
	private IAuthorityService authorityService;
	
	//查询用户的权限集
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		Collection<SimpleGrantedAuthority> userAuths = new ArrayList<SimpleGrantedAuthority>();
		com.core.bean.User loginUser = userService.findUserByName(username);
		List<Authority> list = authorityService.listAuthorityByUserId(loginUser.getId());
		for(Authority item : list){
			userAuths.add(new SimpleGrantedAuthority(item.getAuthorityName()));
		}
		return new User(username, loginUser.getPassword(), true, true, true, true, userAuths);
	}

	
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IAuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(IAuthorityService authorityService) {
		this.authorityService = authorityService;
	}
	
	
}