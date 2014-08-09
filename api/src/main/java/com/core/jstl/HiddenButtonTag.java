package com.core.jstl;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class HiddenButtonTag extends BodyTagSupport {

	private static final long serialVersionUID = 6057425469805261326L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int doStartTag() {
		try {
			
			ServletRequest sq= pageContext.getRequest();
			HttpServletRequest req = (HttpServletRequest)sq ;
			//if(拥有权限){
				return EVAL_BODY_INCLUDE;
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY ;
	}

	public int doEndTag() {
		return EVAL_BODY_INCLUDE;
	}

}
