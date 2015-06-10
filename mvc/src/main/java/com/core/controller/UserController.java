package com.core.controller;

import javax.servlet.http.HttpServletRequest;
import com.core.bean.User;
import com.core.constants.CommonConstants;
import com.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private IUserService userService;
	
	/**AJAX 登录*/
	@ResponseBody
	@RequestMapping(value="/login.json", method=RequestMethod.POST)
	public ModelMap loginUser(String userName, String password, HttpServletRequest request){
		ModelMap modelMap = new ModelMap();
		User user = userService.findUserByName(userName);
		
		if(user == null || ! user.getPassword().equals(password)){
			modelMap.addAttribute(CommonConstants.RE_STATUS, CommonConstants.RE_SUCESS);
			modelMap.addAttribute(CommonConstants.RE_MSG, "用户名/密码错误！");
		} else {
			setCurUser(request, user);//session 
			modelMap.addAttribute(CommonConstants.RE_STATUS, CommonConstants.RE_FALIURE);
		}
    	return modelMap;
	}
    
	/**用户 登出*/
	@RequestMapping(value="/logout.htm", method=RequestMethod.GET)
	public String logoutUser(HttpServletRequest request){
		request.getSession().invalidate();
    	return "redirect:/user/login.htm";
	}

}
