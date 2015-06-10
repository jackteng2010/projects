package com.core.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.core.util.ExcelUtil;

public class FileController extends BaseController {

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
				List<List<Object>> list = ExcelUtil.read(inputStream);
				model.addAttribute("message", new com.google.gson.Gson().toJson(list));
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "文件解析失败");	
			}
    	}
    	return "uploadFile.jsp";
    }
}
