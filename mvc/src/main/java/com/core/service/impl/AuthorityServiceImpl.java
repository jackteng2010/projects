package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.bean.Authority;
import com.core.bean.AuthorityResource;
import com.core.bean.Resource;
import com.core.bean.Role;
import com.core.bean.RoleAuthority;
import com.core.bean.UserRole;
import com.core.dao.ICommonDao;
import com.core.service.IAuthorityService;
import com.core.util.Exp;
import com.core.util.PageParams;
import com.core.util.PageResult;
import com.core.vo.UserRoleVo;

@Service("authorityService")
public class AuthorityServiceImpl implements IAuthorityService {

	@Autowired
	private ICommonDao commonDao;

	@Override
	public void createRole(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRole(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRole(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageResult<Role> listRole(PageParams pageParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createAuthority(Authority authority) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAuthority(Authority authority) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAuthority(Authority authority) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageResult<Authority> listAuthority(PageParams pageParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Authority> listAuthorityByUserId(String userId) {
		return commonDao.list(new Exp(), Authority.class);
	}

	@Override
	public void createResource(Resource resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateResource(Resource resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteResource(Resource resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageResult<Resource> listResource(PageParams pageParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUserRole(UserRole userRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserRole(UserRole userRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUserRole(UserRole userRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageResult<UserRoleVo> listUserRole(PageParams pageParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRoleAuthority(RoleAuthority roleAuthority) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRoleAuthority(RoleAuthority roleAuthority) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRoleAuthority(RoleAuthority roleAuthority) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageResult<RoleAuthority> listRoleAuthority(PageParams pageParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createAuthorityResource(AuthorityResource authorityResource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAuthorityResource(AuthorityResource authorityResource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAuthorityResource(AuthorityResource authorityResource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageResult<RoleAuthority> listAuthorityResource(PageParams pageParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuthorityResource> listAllAuthorityResource() {
		return commonDao.listAll(AuthorityResource.class);
	}
	
}
