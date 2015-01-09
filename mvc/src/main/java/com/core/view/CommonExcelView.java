package com.core.view;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.core.util.ExcelUtil;

public class CommonExcelView extends AbstractExcelView {

	public static final String FILE_URL 	= "fileUrl";	// "/template/template001"
	public static final String FILE_HEADER 	= "fileHeader";	// {"Name","Password","Age",}
	public static final String FILE_NAME 	= "fileName";	// "2014-01-01.xls"
	public static final String FILE_DATA 	= "fileData";	// List<List<Object>>
	
	public static final String SHEET_INDEX 	= "sheetIndex";
	public static final String ROW_INDEX 	= "rowIndex";
	public static final String COLUMN_INDEX = "columnIndex";
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fileUrl = model.containsKey(FILE_URL) ? (String)model.get(FILE_URL) : null;
		String[] fileHeader = model.containsKey(FILE_HEADER) ? (String[])model.get(FILE_HEADER) : null;
		int sheetIndex 	= model.containsKey(SHEET_INDEX) ? (int)model.get(SHEET_INDEX) : 0;
		int rowIndex 	= model.containsKey(ROW_INDEX) ? (int)model.get(ROW_INDEX) : 1;
		int columnIndex = model.containsKey(COLUMN_INDEX) ? (int)model.get(COLUMN_INDEX) : 0;
		
		List<List<Object>> list = model.get(FILE_DATA) instanceof List ? (List<List<Object>>)model.get(FILE_DATA) : new ArrayList<List<Object>>();
		String fileName = model.containsKey(FILE_NAME) ? (String)model.get(FILE_NAME) : String.valueOf(new Date().getTime());
		
		if(fileUrl != null){
			workbook = getTemplateSource(fileUrl, request);
		}
		if(fileHeader != null){
			List<List<Object>> listHead = new ArrayList<List<Object>>();
			List<Object> head = new ArrayList<Object>();
			for(String s : fileHeader){
				head.add(s);
			}
			listHead.add(head);
			ExcelUtil.writeWorkbook(listHead, workbook, sheetIndex, 0, 0);	
		}
        ExcelUtil.writeWorkbook(list, workbook, sheetIndex, rowIndex, columnIndex);
        
		response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1") + ".xls");
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
	}

}
