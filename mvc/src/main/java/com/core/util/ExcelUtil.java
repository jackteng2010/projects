package com.core.util;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * 利用开源组件POI动态导出导出EXCEL文档
 * 
 * @author J.T
 * @version v1.0
 * 
 * --------------------------------基础方法-------------------------------------------------
1. 获取工作薄		无参数
2. 获取wookBook	必要参数： filePath
3. 获取wookBook	必要参数：inputStream
4. 读单元格内容		必要参数：row	coloumIndex
5. 写单元格内容		必要参数：cell	value
6. 读工作薄		必要参数：wookbook						附加参数：sheetIndex,rowIndex,columnIndex
7. 写工作薄		必要参数：wookbook list					附加参数：sheetIndex,rowIndex,columnIndex
-----------------------------------一次扩展方法-----------------------------------------------
1. 读文件			必要参数：filePath						附加参数：sheetIndex,rowIndex,columnIndex
2. 读输入流		必要参数：inputStream					附加参数：sheetIndex,rowIndex,columnIndex
3. 写文件			必要参数：filePath List					附加参数：sheetIndex,rowIndex,columnIndex
4. 写输出流		必要参数：outputStream	List 			附加参数：sheetIndex,rowIndex,columnIndex
4. 读文件 写输出流	必要参数：filePath outputStream	 List	附加参数：sheetIndex,rowIndex,columnIndex
-----------------------------------三次扩展方法-(sheetIndex=0, rowIndex=1, columnIndex=0)----
 * 
 */
public class ExcelUtil {

	//------------------------------------基础方法------------------------------------------
	
	/**获取工作薄对象*/
	public static Workbook getWorkBook(){
		return new HSSFWorkbook();
	}
	
	/**获取工作薄对象*/
	public static Workbook getWorkBook(String filePath){
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(new File(filePath));
		} catch (InvalidFormatException e) {
			throw new RuntimeException("Error to create workbook, because of invalid format", e);
		} catch (IOException e) {
			throw new RuntimeException("Error to create workbook, because of io exception", e);
		}
		return wb;
	}
	
	/**获取工作薄对象*/
	public static Workbook getWorkBook(InputStream inputStream){
        Workbook wb = null;
        try {
			wb = WorkbookFactory.create(inputStream);
		} catch (InvalidFormatException e) {
			throw new RuntimeException("Error to create workbook, because of invalid format", e);
		} catch (IOException e) {
			throw new RuntimeException("Error to create workbook, because of io exception", e);
		}
        return wb;
	}
	
	/** 设置单元格内容 */
	public static void setCellValue(Cell cell, Object value){
		setCellValue(cell, value, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 设置单元格内容
	 * @param cell 单元格对象
	 * @param value 内容
	 * @param dateFormat 日期类型转为字符串的格式 	例： yyyy-MM-dd   yyyy-MM-dd hh:mm:ss
	 * 
	 * */
	public static void setCellValue(Cell cell, Object value, String dateFormat){
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
            cell.setCellValue(String.valueOf(value));
        }else if(value instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            cell.setCellValue(sdf.format(value));
        }else if(value == null){
            //TODO: cell.setCellValue("");
        }else{
        	cell.setCellValue(value.toString());
        }
    }
	
	/**
	 * 获取单元格内容
	 * 
	 * @param row 行对象
	 * @param columnNum 第几列 0 ~ N
	 * 
	 * */
    public static Object getCellValue(Row row, int columnNum) {
		Cell cell = row.getCell(columnNum);
		Object cellValue = null;
		if(cell == null){
			return cellValue;
		}
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC:
            	cellValue = cell.getNumericCellValue();
                break;
            case XSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case XSSFCell.CELL_TYPE_ERROR:
            	//TODO: WHETHER THROW EXCEPTION
                cellValue = null;
                break;
            default:
            	//TODO: WHETHER THROW EXCEPTION
                cellValue = null;	
                break;
        }
        return cellValue;
    }
	
	/**
	 * 读工作薄
	 * 
	 * @param workbook
	 * @param sheetIndex 	第几个sheet 0 ~ N
	 * @param rowIndex 		起始行 0 ~ N
	 * @param columnIndex 	起始列0 ~ N
	 * 
	 * */
	public static List<List<Object>> readWorkbook(Workbook workbook, int sheetIndex, int rowIndex, int columnIndex){
		List<List<Object>> list = new ArrayList<List<Object>>();
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		for(int i=rowIndex; i <= sheet.getLastRowNum(); i++){
			Row row = sheet.getRow(i);
			List<Object> subList = new ArrayList<Object>();
			for(int j = columnIndex; j < row.getLastCellNum(); j++){//getLastCellNum 是获取最后一个不为空的列是第几个
	            subList.add(getCellValue(row, j));
            }
			list.add(subList);
		}
		return list;
	}
	
	/**
	 * 写工作薄
	 * 
	 * @param sheetIndex 	第几个sheet 0 ~ N
	 * @param rowIndex 		起始行 0 ~ N
	 * @param columnIndex 	起始列0 ~ N
	 * 
	 * */
	public static void writeWorkbook(List<List<Object>> list, Workbook workbook, int sheetIndex, int rowIndex, int columnIndex){
		Sheet sheet = null;
		try{
			sheet = workbook.getSheetAt(sheetIndex);
		} catch(Exception e){
			sheet = workbook.createSheet();
		}
		
		for(int i=0; i < list.size(); i++){
			List<Object> subList = list.get(i);
			if(subList != null){
				Row row = sheet.getRow(rowIndex + i);
				row = (row==null)? sheet.createRow(rowIndex + i) : row;
				for(int j=0; j < subList.size(); j++){
		            Cell cell = row.getCell(columnIndex + j);
		            if(cell == null){
		            	cell = row.createCell(columnIndex + j);		            	
		            }
		            setCellValue(cell, subList.get(j));
	            }
			}
		}
	}
	
    //-----------------------------------一次扩展方法------------------------------------------
	
	/** 读取文件 */
    public static List<List<Object>> read(String filePath, int sheetIndex, int rowIndex, int columnIndex){
    	return readWorkbook(getWorkBook(filePath), sheetIndex, rowIndex, columnIndex); 
    }
    
	/** 读取输入流*/
    public static List<List<Object>> read(InputStream inputStream, int sheetIndex, int rowIndex, int columnIndex){
    	return readWorkbook(getWorkBook(inputStream), sheetIndex, rowIndex, columnIndex);
    }
    
    /**写入文件*/
    public static void write(List<List<Object>> list, String filePath, int sheetIndex, int rowIndex, int columnIndex){
    	Workbook workbook = getWorkBook(filePath);
    	writeWorkbook(list, workbook, sheetIndex, rowIndex, columnIndex);
    	OutputStream outputSteam = null;
    	try {
			workbook.write(outputSteam);
		} catch (IOException e) {
			throw new RuntimeException("Error to write workbook, because of io exception", e);
		} finally {
			if(outputSteam != null){
				try {
					outputSteam.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
    /**写入输出流*/
    public static void write(List<List<Object>> list, OutputStream outputSteam, int sheetIndex, int rowIndex, int columnIndex){
    	Workbook workbook = getWorkBook();
    	writeWorkbook(list, workbook, sheetIndex, rowIndex, columnIndex);
    	try {
			workbook.write(outputSteam);
		} catch (IOException e) {
			throw new RuntimeException("Error to write workbook, because of io exception", e);
		}
    }
    
    /**
     * 读文件 写输出流
     * 
     * @param	list			数据集
     * @param	filePath 		模版文件路径
     * @param 	outputStream	输出流
     * @param 	sheetIndex		第几个sheet
     * @param 	rowIndex		开始行0~n
     * @param  	column			开始列0~n
     * */
    public static void write(List<List<Object>> list, String filePath, OutputStream outputSteam, int sheetIndex, int rowIndex, int columnIndex){
    	Workbook workbook = getWorkBook(filePath);
    	writeWorkbook(list, workbook, sheetIndex, rowIndex, columnIndex);
    	try {
			workbook.write(outputSteam);
		} catch (IOException e) {
			throw new RuntimeException("Error to write workbook, because of io exception", e);
		}
    }    
    
    //------------------------------------二次扩展方法 默认自第1行第0列(A2) 开始读写--------------------------------------------------
	
    /**读工作薄*/
    public static List<List<Object>> readWorkbook(Workbook workbook){
    	return readWorkbook(workbook, 0, 1, 0);
    }
    
    /**写工作薄*/
    public static void writeWorkbook(List<List<Object>> list, Workbook workbook){
    	writeWorkbook(list, workbook, 0, 1, 0);
    }
	/**读取文件*/
    public static List<List<Object>> read(String filePath){
    	return read(filePath, 0, 1, 0); 
    }
    
	/**读输入流 */
    public static List<List<Object>> read(InputStream inputStream){
    	return read(inputStream, 0, 1, 0);
    }
    
    /**写文件*/
    public static void write(List<List<Object>> list, String filePath){
    	write(list, filePath, 0, 1, 0);
    }
    
    /**写输出流*/
    public static void write(List<List<Object>> list, OutputStream outputSteam){
    	write(list, outputSteam, 0, 1, 0);
    }
    
    /**读文件 写输出流*/
    public static void write(List<List<Object>> list, String filePath, OutputStream outputSteam){
    	write(list, filePath, outputSteam, 0, 1, 0);
    }
    
}