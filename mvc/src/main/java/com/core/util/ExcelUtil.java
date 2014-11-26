package com.core.util;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * 利用开源组件POI3.0.2动态导出导出EXCEL文档
 * 
 * @author JACK.TENG
 * @version v1.0
 */
public class ExcelUtil {

	/**
	 * 读文件 默认起始位置第1行第0列 A2单元格
	 * 
	 * @param filePath 	文件路径  例:C://demo.xlss
	 * 
	 */
	public static List<List<Object>> readFile(String filePath){
		return readFile(filePath, 0, 1, 0);
	}
	
	/**
	 * 读文件
	 * 
	 * @param filePath 		文件路径  例:C://demo.xlss
	 * @param sheetIndex 	第几个sheet 0 ~ N
	 * @param rowIndex 		起始行 0 ~ N
	 * @param coloumIndex 	起始列0 ~ N
	 * 
	 * */
	public static List<List<Object>> readFile(String filePath, int sheetIndex, int rowIndex, int coloumIndex){
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filePath);
			return readInputStream(inputStream, sheetIndex, rowIndex, coloumIndex);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("happen exception file not found [" + filePath + "]", e);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/** 读输入流， 默认起始位置第1行第0列 A2单元格
	 * 
	 * @param inputStream
	 * 
	 */
	public static List<List<Object>> readInputStream(InputStream inputStream){
		return readInputStream(inputStream, 0, 1, 0);
	}
	
	/**
	 * 读输入流
	 * 
	 * @param inputStream 	文件输入流
	 * @param sheetIndex 	第几个sheet 0 ~ N
	 * @param rowIndex 		起始行 0 ~ N
	 * @param coloumIndex 	起始列0 ~ N
	 * 
	 * */
	public static List<List<Object>> readInputStream(InputStream inputStream, int sheetIndex, int rowIndex, int coloumIndex){
		List<List<Object>> list = new ArrayList<List<Object>>();
		try {
			HSSFWorkbook wookBook = new HSSFWorkbook(inputStream);
			if(wookBook != null){
				HSSFSheet sheet = wookBook.getSheetAt(sheetIndex);
				for(int i=rowIndex; i <= sheet.getLastRowNum(); i++){
					HSSFRow row = sheet.getRow(i);
					List<Object> subList = new ArrayList<Object>();
					for(int j = coloumIndex; j < row.getLastCellNum(); j++){//getLastCellNum 是获取最后一个不为空的列是第几个
			            subList.add(getCellValue(row, j));
		            }
					list.add(subList);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("happen io exception when read excel file", e);
		}
		return list;
	}
	
	//------------------写操作----------------------------
	
	/**
	 * 写文件，默认起始位置第1行第0列A2
	 * 
	 * @param filePath 文件路径
	 * @param list 数据集合
	 * 
	 */
	public static void writeFile(String filePath, List<List<Object>> list){
		writeFile(filePath, 0, 1, 0, list);
	}
	
	
	/**
	 * 写文件
	 * 
	 * @param filePath 		文件路径  例:C://demo.xlss
	 * @param sheetIndex 	第几个sheet 0 ~ N
	 * @param rowIndex 		起始行 0 ~ N
	 * @param coloumIndex 	起始列0 ~ N
	 * @param list			数据集
	 * 
	 * */
	public static void writeFile(String filePath, int sheetIndex, int rowIndex, int coloumIndex, List<List<Object>> list){
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null; 
		HSSFWorkbook wookBook = null;
		try {
			inputStream = new FileInputStream(filePath);
			wookBook = new HSSFWorkbook(inputStream);
			HSSFSheet sheet = wookBook.getSheetAt(sheetIndex);
				
			for(int i=0; i < list.size(); i++){
				List<Object> subList = list.get(i);
				HSSFRow row = sheet.getRow(rowIndex + i);
				if(row == null) {
					row = sheet.createRow(rowIndex + i);
				}
				for(int j=0; j < subList.size(); j++){
		            HSSFCell cell = row.getCell(coloumIndex + j);
		            if(cell == null) {
		            	cell = row.createCell(coloumIndex + j);
		            }
		            setCell(cell, subList.get(j));
	            }
			}
			outputStream = new FileOutputStream(filePath);
			wookBook.write(outputStream);
		} catch (IOException e) {
			throw new RuntimeException("happen io exception when write excel file", e);
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 写进输出流，默认起始位置第1行第0列A2
	 * 
	 * @param outputStream
	 * @param list 数据集合
	 * 
	 */
	public static void writeOutputStream(OutputStream outputStream, List<List<Object>> list){
		writeOutputStream(outputStream, 0, 1, 0, list);
	}
	
	/**
	 * 写进输出流
	 * 
	 * @param outputStream 
	 * @param sheetIndex 	第几个sheet 0 ~ N
	 * @param rowIndex 		起始行 0 ~ N
	 * @param coloumIndex 	起始列0 ~ N
	 * @param list			数据集
	 * 
	 * */
	public static void writeOutputStream(OutputStream outputStream, int sheetIndex, int rowIndex, int coloumIndex, List<List<Object>> list){
		HSSFWorkbook wookBook = null;
		try {
			wookBook = new HSSFWorkbook();
			HSSFSheet sheet = wookBook.getSheetAt(sheetIndex);
			
			for(int i=0; i < list.size(); i++){
				List<Object> subList = list.get(i);
				HSSFRow row = sheet.createRow(rowIndex + i);
				
				for(int j=0; j < subList.size(); j++){
		            HSSFCell cell = row.createCell(coloumIndex + j);
		            setCell(cell, subList.get(j));
	            }
			}
			wookBook.write(outputStream);
		} catch (IOException e) {
			throw new RuntimeException("happen io exception when write excel file", e);
		}
		
	}
	//-------------------------Base Method----------------------------------
	
	/**
	 * 设置单元格内容
	 * 
	 * */
	public static void setCell(HSSFCell cell, Object value){
		setCell(cell, value, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static void setCell(HSSFCell cell, Object value, String dateFormat){
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
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            cell.setCellValue(sdf.format(value));
        }else if(value == null){
            cell.setCellValue("");
        }else{
        	cell.setCellValue(value.toString());
        }
    }
	
	/**
	 * 获取单元格内容
	 * 
	 * */
    public static Object getCellValue(HSSFRow row, int columnNum) {
		HSSFCell cell = row.getCell(columnNum);
		Object cellValue = null;
		if(cell == null){
			return cellValue;
		}
		
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
                cellValue = null;
                break;
            default:
            	//TODO: THROW EXCEPTION
                cellValue = null;
                break;
        }
        return cellValue;
    }
    
	/**
	 * 通过文件路径获取工作薄对象
	 **/
	public static Workbook getWorkBook(String filePath){
		Workbook wb = null;
		try {
			wb = getWorkBook(new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Happen exception file not found [" + filePath + "]", e);
		}
		return wb;
	}
	
	/**
	 * 通过文件输入流获取工作薄对象
	 **/
	public static Workbook getWorkBook(FileInputStream fileInputStream){
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(fileInputStream);
        } catch (Exception e) {
        	throw new RuntimeException("Happen exception file can not found", e);
        }
        return wb;
	}
	
}