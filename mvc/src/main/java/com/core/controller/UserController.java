package com.core.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.core.bean.User;
import com.core.service.IUserService;
import com.core.util.ExcelUtil;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    
    @RequestMapping(value="/uploadFile.htm", method=RequestMethod.GET)
    public String uploadFileGet( Model model) {
    	return "uploadFile.jsp";
    }
    
    /**xls文件上传解析*/
    @RequestMapping(value="/uploadFile.do", method=RequestMethod.POST)
    public String uploadFilePost(@RequestParam MultipartFile uploadFile, String fileName, Model model) {
    	if(! uploadFile.getOriginalFilename().endsWith(".xls")){
    		model.addAttribute("message", "文件类型错误，仅支持.xls");
    	} else {
			try {
				InputStream inputStream = uploadFile.getInputStream();
				List<List<Object>> list = ExcelUtil.readInputStream(inputStream);
				model.addAttribute("message", new com.google.gson.Gson().toJson(list));
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "文件解析失败");	
			}
    	}
    	return "uploadFile.jsp";
    }
    
    
    
}
