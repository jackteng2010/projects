package com.core.controller;

import com.core.constants.CommonConstants;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class DefaultController extends BaseController {
	
    @RequestMapping(value="", method=RequestMethod.GET)
    public String defaultPage(Model model) {
    	model.addAttribute(CommonConstants.CUR_PAGE, "index.jsp");
    	return "index.jsp";
    }
	
    @RequestMapping(value="index.htm", method=RequestMethod.GET)
    public String index(Model model) {
    	model.addAttribute(CommonConstants.CUR_PAGE, "index.jsp");
    	return "index.jsp";
    }
    
	@RequestMapping(value="login.htm", method=RequestMethod.GET)
	public String loginPage(){
		return "login.jsp";
	}
	
}
