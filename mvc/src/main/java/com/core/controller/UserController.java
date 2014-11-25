package com.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.core.bean.User;
import com.core.service.IUserService;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
    @RequestMapping("/login.do")
    public String login(@RequestParam String userName, @RequestParam String password, Model model, HttpServletRequest request) {
    	//check permission,then...
    	User user = userService.findUserByName(userName);
    	if(! user.getPassword().equals(password)){
    		return "redirect:../login.html?errorMsg=password is wrong.";
    	}
    	request.getSession().setAttribute("curUser", user);
    	request.getSession().setAttribute("curUserId", user.getId());
    	request.getSession().setAttribute("curUserName", user.getUserName());
		return "redirect:index.htm";
    }
    
    @RequestMapping("/index.htm")
    public String index(Model model, HttpServletRequest request) {
    	if(request.getSession().getAttribute("curUserId") == null){
    		return "redirect:../login.html";
    	}
    	return "index.jsp";
    }
    
    /**接受ajax访问*/
    @ResponseBody
    @RequestMapping(value="/list.json",method=RequestMethod.GET)
    public Map<String,Object> list(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("userName", "admin");
        result.put("password", "123456");
        result.put("age", 100);
        return result;
    }

    @ResponseBody
    @RequestMapping("/batchUpdate.json")
    public List<User> batchUpdateUser(@RequestBody List<User> userList) {
    	System.out.println(new Gson().toJson(userList));
        return userList;
    }
}
