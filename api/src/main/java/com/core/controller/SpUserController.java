package com.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.core.service.IUserService;

@Controller
@RequestMapping("/spuser")
public class SpUserController {
	
	@Autowired
    private IUserService userService;
    
}
