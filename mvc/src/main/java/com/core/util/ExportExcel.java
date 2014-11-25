package com.core.util;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author J.T
 * @version v1.0
 * @param <T>
 */
public class ExportExcel {

	public static final String UNKNOW_CELL_TYPE = "未知类型";
	
	public static final String UNKNOW_CELL_VALUE = "非法字符";
	
	/**读取文件流*/
	public static List<List<Object>> read(FileInputStream fileInputStream, int sheetIndex, int rowIndex, int coloumIndex){
		HSSFWorkbook wookBook = (HSSFWorkbook)getWorkBook(fileInputStream);
		List<List<Object>> list = new ArrayList<List<Object>>();
		if(wookBook != null){
			HSSFSheet sheet = wookBook.getSheetAt(sheetIndex);
			for(int i=0; i <= sheet.getLastRowNum(); i++){
				HSSFRow row = sheet.getRow(sheetIndex + i);
				List<Object> subList = new ArrayList<Object>();
				for(int j=0; j <= row.getLastCellNum(); j++){
		            subList.add(getRowValue(row, coloumIndex + j));
	            }
				list.add(subList);
			}
		}
		return list;
	}
	
	/**读写文件流**/
	public static void readAndwrite(FileInputStream fileInputStream, String[] header, int sheetIndex, int rowIndex, int coloumIndex, List<List<Object>> list){
		if(header != null){
			List<Object> hList = new ArrayList<Object>();
			for(String s : header){
				hList.add(s);
			}
			list.add(0, hList);
		}
		readAndwrite(fileInputStream, sheetIndex, rowIndex, coloumIndex, list);
	}
	
	/**写文件流*/
	public static void readAndwrite(FileInputStream fileInputStream, int sheetIndex, int rowIndex, int coloumIndex, List<List<Object>> list){
		HSSFWorkbook wookBook = (HSSFWorkbook)getWorkBook(fileInputStream);
		if(wookBook != null){
			HSSFSheet sheet = wookBook.getSheetAt(sheetIndex);
			for(int i=0; i < list.size(); i++){
				List<Object> subList = list.get(i);
				HSSFRow row = sheet.createRow(sheetIndex + i);
				for(int j=0; j < subList.size(); j++){
		            HSSFCell cell = row.createCell(coloumIndex + j);
		            setCell(cell, subList.get(j));
	            }
			}
		}
	}
	
	public static void setCell(HSSFCell cell,Object value){
        if(value instanceof Integer){
            cell.setCellValue(((Integer)value).intValue());
        }else if(value instanceof Double){
            cell.setCellValue(((Double)value).doubleValue());
        }else if(value instanceof Float){
            cell.setCellValue(((Float)value).floatValue());
        }else if(value instanceof Long){
            cell.setCellValue(((Long)value).longValue());
        }else if(value instanceof Boolean){
            cell.setCellValue(((Boolean)value).booleanValue());
        }else if(value instanceof String){
            cell.setCellValue((String)value);
        }else if(value instanceof Date){
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cell.setCellValue(sdf.format(value));
        }else if(value == null){
            cell.setCellValue("");
        }else{
        	cell.setCellValue(value.toString());
        }
    }
	
    public static Object getRowValue(HSSFRow row, int columnNum) {
		HSSFCell cell = row.getCell(columnNum);
		Object cellValue = null;
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC:
            	cellValue = cell.getNumericCellValue();
                break;
            case XSSFCell.CELL_TYPE_STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA: // 公式
                cellValue = cell.getCellFormula();
                break;
            case XSSFCell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case XSSFCell.CELL_TYPE_ERROR: // 故障
            	//TODO: THROW EXCEPTION
                cellValue = "非法字符";
                break;
            default:
            	//TODO: THROW EXCEPTION
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
    
	/**
	 * 获取工作薄对象
	 **/
	public static Workbook getWorkBook(String filePath){
		Workbook wb = null;
		try {
			wb = getWorkBook(new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	public static Workbook getWorkBook(FileInputStream fileInputStream){
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(fileInputStream);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
        		fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return wb;
	}
	
}