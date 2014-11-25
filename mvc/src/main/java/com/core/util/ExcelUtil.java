package com.core.util;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelUtil {

	public void exportExcel(String title, String[] headers, List<List<Object>> dataset, OutputStream out, String templateFilePath) {
       
        HSSFWorkbook workbook = new HSSFWorkbook(); // 声明一个工作薄
        
        HSSFSheet sheet = workbook.createSheet(title);// 生成一个表格
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
	
	}
	
    /**
     * 
     * 功能描述: 检测excel类型 <br>
     * 〈功能详细描述〉
     * 
     * @param filePath 文件路径
     * @return flag 结果
     */
    public static boolean isExcel(String filePath) {
        String xlsReg = "^.+\\.(?i)(xls)$";
        String xlsxReg = "^.+\\.(?i)(xlsx)$";
        return filePath.matches(xlsReg) || filePath.matches(xlsxReg) ? true : false;
    }

    /**
     * 
     * 功能描述:检查文件是否存在 <br>
     * 〈功能详细描述〉
     * 
     * @param filePath 文件路径
     * @return 是否
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean fileExist(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 功能描述:依据内容判断是否为excel2003及以下 <br>
     * 〈功能详细描述〉
     * 
     * @param excelFileInputStream 表格流
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isExcel2003(FileInputStream excelFileInputStream) {
        try {
            BufferedInputStream bis = new BufferedInputStream(excelFileInputStream);
            if (POIFSFileSystem.hasPOIFSHeader(bis)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 
     * 功能描述:依据内容判断是否为excel2007及以上 <br>
     * 〈功能详细描述〉
     * 
     * @param excelFileInputStream 表格流
     * @return 是否
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isExcel2007(FileInputStream excelFileInputStream) {
        try {
            BufferedInputStream bis = new BufferedInputStream(excelFileInputStream);
            if (POIXMLDocument.hasOOXMLHeader(bis)) {
                return true;
            }
        } catch (IOException e) {
        	e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 
     * 功能描述: <br>
     * 获取某单元的值
     * 
     * @param row 行数
     * @param columnNum 列数
     * @return cellValue 单元值
     */
    public static String getRowValue(Row row, int columnNum) {
        Cell cell = row.getCell(columnNum);
        Object cellValue = "";
        if (null != cell) {
            switch (cell.getCellType()) {		// 以下是判断数据的类型
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
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
                    break;
            }

        }
        return cellValue.toString();
    }

    /**
     * 
     * 功能描述: <br>
     * 获取excel对象
     * 
     * @param excelFileInputStream 文件流
     * @return wb excel对象
     */
    public static Workbook getExcelFileInputStream(FileInputStream excelFileInputStream) {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(excelFileInputStream);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (excelFileInputStream != null) {
                try {
                    excelFileInputStream.close();
                } catch (IOException e) {
                	e.printStackTrace();
                }
            }
        }
        return wb;
    }
    
    /**
     * 创建Excel对象并填充数据
     * 
     * */
    public static HSSFWorkbook writeExcel(List<List<Object>> data){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        if(data == null || data.isEmpty()){
        	return workbook;
        }
    	for(int i = 0; i < data.size(); i++){
    		HSSFRow row = sheet.createRow(i);//创建 行
    		List<Object> rowData = data.get(i);
    		for(int j = 0; j < rowData.size(); j++){
    			Object cellData = rowData.get(j);//创建单元格
    			HSSFCell cell = row.createCell(i);
    			setCell(cell, cellData);//填塞内容
    		}
    	}
        return workbook;
    }
    
    /**
     * 给单元内容赋值
     * */
    private static void setCell(HSSFCell cell, Object value){
        if(value instanceof Integer){
            cell.setCellValue(((Integer)value).intValue());
        } else if(value instanceof String){
            cell.setCellValue((String)value);
        } else if(value instanceof Double){
            cell.setCellValue(((Double)value).doubleValue());
        } else if(value instanceof Float){
            cell.setCellValue(((Float)value).floatValue());
        } else if(value instanceof Long){
            cell.setCellValue(((Long)value).longValue());
        } else if(value instanceof Boolean){
            cell.setCellValue(((Boolean)value).booleanValue());
        } else if(value == null){
            cell.setCellValue("");
        } else if(value instanceof Date){
            cell.setCellValue((Date)value);
        }
    }

}