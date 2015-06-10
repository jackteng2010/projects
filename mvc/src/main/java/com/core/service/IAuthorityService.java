package com.core.service;

import java.util.List;
import com.core.bean.Authority;
import com.core.bean.AuthorityResource;
import com.core.bean.Resource;
import com.core.bean.Role;
import com.core.bean.RoleAuthority;
import com.core.bean.UserRole;
import com.core.util.PageParams;
import com.core.util.PageResult;
import com.core.vo.UserRoleVo;

public interface IAuthorityService {

	//-------------角色----------------
	public void createRole(Role role);
	public void updateRole(Role role);
	public void deleteRole(Role role);
	public PageResult<Role> listRole(PageParams pageParams);
	
	//---------------权限--------------------
	public void createAuthority(Authority authority);
	public void updateAuthority(Authority authority);
	public void deleteAuthority(Authority authority);
	public PageResult<Authority> listAuthority(PageParams pageParams);
	public List<Authority> listAuthorityByUserId(String userId);
	
	//--------------资源---------------------
	public void createResource(Resource resource);
	public void updateResource(Resource resource);
	public void deleteResource(Resource resource);
	public PageResult<Resource> listResource(PageParams pageParams);
	
	//-----------用户+角色------------------------
	public void createUserRole(UserRole userRole);
	public void updateUserRole(UserRole userRole);
	public void deleteUserRole(UserRole userRole);
	public PageResult<UserRoleVo> listUserRole(PageParams pageParams);
	
	//-------------角色+权限------------------------------
	public void createRoleAuthority(RoleAuthority roleAuthority);
	public void updateRoleAuthority(RoleAuthority roleAuthority);
	public void deleteRoleAuthority(RoleAuthority roleAuthority);
	public PageResult<RoleAuthority> listRoleAuthority(PageParams pageParams);
	
	//---------------权限+资源--------------------
	public void createAuthorityResource(AuthorityResource authorityResource);
	public void updateAuthorityResource(AuthorityResource authorityResource);
	public void deleteAuthorityResource(AuthorityResource authorityResource);
	public PageResult<RoleAuthority> listAuthorityResource(PageParams pageParams);
	public List<AuthorityResource> listAllAuthorityResource();
	
	
}
