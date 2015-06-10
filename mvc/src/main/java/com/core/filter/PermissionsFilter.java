package com.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.core.constants.CommonConstants;

public class PermissionsFilter implements Filter {

	private static final String FILTER_REQUEST_FLAG = "@filter_request_flag_onece";
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		// 保证该过滤器在一次请求中只被调用一次
		if(request != null && request.getAttribute(FILTER_REQUEST_FLAG) != null){
			filterChain.doFilter(request, response);
		} else {
			request.setAttribute(FILTER_REQUEST_FLAG, Boolean.TRUE);
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String url = httpRequest.getRequestURI();
			
			//登录权限验证
			
			//其他权限验证
			System.out.println(url);
			//临时方案
			if("/user/login.json".equals(url) && httpRequest.getAttribute(CommonConstants.CUR_USER) == null){
				request.getRequestDispatcher("login.html").forward(request, response);
				return;
			}
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
