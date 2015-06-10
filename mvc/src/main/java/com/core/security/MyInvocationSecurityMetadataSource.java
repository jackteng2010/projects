package com.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import com.core.bean.AuthorityResource;
import com.core.service.IAuthorityService;
import com.core.service.IUserService;

/**
 * URL与权限映射集合
 **/
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	//private UrlMatcher urlMatcher = new AntUrlPathMatcher();;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	private IUserService userService;
	private IAuthorityService authorityService;
	
	//加载URL 1:n 权限集合
	private void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<AuthorityResource> list = authorityService.listAllAuthorityResource();
		if(list != null){
			for(AuthorityResource item : list){
				String path = item.getResource().getResourcePath();
				if(! path.startsWith("/")){
					path = "/" + path;
				}
				if(path.endsWith("/")){
					path = path.substring(0, path.length()-1);
				}
				if(! resourceMap.containsKey(path)){
					resourceMap.put(path, new ArrayList<ConfigAttribute>());
				}
				resourceMap.get(path).add(new SecurityConfig(item.getAuthority().getAuthorityName()));
			}
		}
	}

	//获取当前URL的 权限集合
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// guess object is a URL.
		String path = ((FilterInvocation)object).getRequestUrl();
		if(! path.startsWith("/")){
			path = "/" + path;
		}
		if(path.endsWith("/")){
			path = path.substring(0, path.length()-1);
		}
		if(resourceMap == null){
			loadResourceDefine();
		}
		return resourceMap.get(path);
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
	
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		if(resourceMap == null){
			return null;
		} else {
			Collection<ConfigAttribute> o = new ArrayList<ConfigAttribute>();
			for(Collection<ConfigAttribute> item : resourceMap.values()){
				o.addAll(item);
			}
			return o;
		}
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