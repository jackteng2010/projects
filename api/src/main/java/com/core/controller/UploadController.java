package com.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.core.util.FileUtil;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@RequestMapping(value="/{config}")
	public ModelAndView uploadFile(HttpServletRequest request, @PathVariable String config, @RequestParam("file") MultipartFile file){
		ModelAndView view = new ModelAndView("jsonView");
		if(!file.isEmpty()){
			String url = FileUtil.uploadFileURL(request, file, config, null);
			view.addObject("file", url);
		}
		return view;
	}
	
	
}
