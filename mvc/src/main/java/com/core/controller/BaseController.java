package com.core.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.core.bean.User;
import com.core.constants.CommonConstants;
import com.core.util.AssertUtil;

public class BaseController {

	protected void setCurUser(HttpServletRequest request, User user){
		request.getSession().setAttribute(CommonConstants.CUR_USER, user);
	}
	
	protected User getCurUser(HttpServletRequest request){
		return (User)request.getSession().getAttribute(CommonConstants.CUR_USER);
	}
	
	protected String getCurUserId(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute(CommonConstants.CUR_USER);
		if(user != null){
			return user.getId();
		}
		return null;
	}
	
	public final String getFullUrl(HttpServletRequest request, String url){
		AssertUtil.hasLength(url, "url不能为空");
		AssertUtil.isTrue(url.startsWith("/"), "url必须以/打头");
		return request.getContextPath() + url;
	}
	
	/**通用接口，Excel数据导出，详情参考{@code CommonExcelView}
	 * @param fileUrl	模版名称
	 * @param data 		模版数据
	 * */
	protected String returnCommonExcel(Model model, String fileUrl, List<List<Object>> data){
		model.addAttribute("fileUrl", fileUrl);
		model.addAttribute("data", data);
		return "commonExcelView";
	}
	
}
