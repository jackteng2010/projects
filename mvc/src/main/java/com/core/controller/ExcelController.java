package com.core.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;


@Controller
@RequestMapping("/excel")
public class ExcelController extends BaseController {

    @RequestMapping("/index.excel")
    public String index(Model model, HttpServletRequest request) {
    	List<List<Object>> data = new ArrayList<List<Object>>();
    	List<Object> subList = new ArrayList<Object>();
    	subList.add("aaaaa");
    	subList.add("bbbbb");
    	subList.add("ccccc");
    	data.add(subList);
    	
    	model.addAttribute("fileData", data);
    	model.addAttribute("fileUrl", "/template/Template01");
    	model.addAttribute("fileName", "中国人");
    	return COMMON_EXCEL_VIEW;
    	
    }
}
