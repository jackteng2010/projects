/*package com.saic.csc.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meidusa.toolkit.common.util.StringUtil;
import com.saic.csc.service.exception.CSCErrorCode;
import com.saic.csc.service.exception.CSCErrorMsg;
import com.saic.csc.service.exception.ReadExcelException;
import com.saic.ebiz.csc.service.constants.CSCConstants;

*//**
 * 〈一句话功能简述〉<br>
 * Excel工具类
 * 
 * @author 柳励
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 *//*
public final class ExcelUtil {

    *//**
     * 空值
     *//*
    private static final String IS_NULL = "为空值";

    *//**
     * 日志组件
     *//*
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    *//**
     * 读写Excel异常信息
     *//*
    private static String exceptionMsg = "读写Excel异常：";

    public static boolean checkExcelStream(FileInputStream excelFileInputStream) {
        return excelFileInputStream == null ? false : true;
    }

    *//**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param filePath 文件路径
     * @return flag 结果
     *//*
    public static boolean isExcel(String filePath) {
        String xlsReg = "^.+\\.(?i)(xls)$";
        String xlsxReg = "^.+\\.(?i)(xlsx)$";
        return filePath.matches(xlsReg) || filePath.matches(xlsxReg) ? true : false;
    }

    *//**
     * 
     * 功能描述:检查文件是否存在 <br>
     * 〈功能详细描述〉
     * 
     * @param filePath 文件路径
     * @return 是否
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     *//*
    public static boolean fileExist(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    *//**
     * 
     * 功能描述:依据内容判断是否为excel2003及以下 <br>
     * 〈功能详细描述〉
     * 
     * @param excelFileInputStream 表格流
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     *//*
    public static boolean isExcel2003(FileInputStream excelFileInputStream) {
        try {
            BufferedInputStream bis = new BufferedInputStream(excelFileInputStream);
            if (POIFSFileSystem.hasPOIFSHeader(bis)) {
                return true;
            }
        } catch (IOException e) {
            logger.error(exceptionMsg, e);
            return false;
        }
        return false;
    }

    *//**
     * 
     * 功能描述:依据内容判断是否为excel2007及以上 <br>
     * 〈功能详细描述〉
     * 
     * @param excelFileInputStream 表格流
     * @return 是否
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     *//*
    public static boolean isExcel2007(FileInputStream excelFileInputStream) {
        try {
            BufferedInputStream bis = new BufferedInputStream(excelFileInputStream);
            if (POIXMLDocument.hasOOXMLHeader(bis)) {
                return true;
            }
        } catch (IOException e) {
            logger.error(exceptionMsg, e);
            return false;
        }
        return false;
    }

    *//**
     * 
     * 功能描述: <br>
     * 校验excel中的值，判断是否有控
     * 
     * @param rowCount 行数
     * @param sheet sheet对象
     * @return results 结果集
     *//*
    public static String[] checkExcelValue(int rowCount, Sheet sheet) {
        String[] results = new String[] { CSCConstants.SUCCESS_RESULT, CSCConstants.SUCCESS_RESULT };
        //取第一行标题列的值
        Row row = sheet.getRow(0);
        int celCnt = row.getPhysicalNumberOfCells();
        for (int i = 1; i < rowCount; i++) {
            row = sheet.getRow(i);
            logger.info("==获得行列的数据==celCnt="+celCnt);
            if (null == row) {
                results[0] = CSCConstants.FAIL_RESULT;
                results[1] = exceptionMsg;
                break;
            }
            int rowNum = i + 1;

            for (int c = 0; c < celCnt; c++) {
            	//经销商传真 、经销商电子邮件、法人、法人手机等可以为空
            	if(c != 7 && c != 8 && c != 10 && c != 11){
                    String value = getRowValue(row, c);
                    if (StringUtil.isEmpty(value.trim())) {
                        results[0] = CSCConstants.FAIL_RESULT;
                        results[1] = getErrorMsgForExcel(rowNum, c, value, IS_NULL);
                        break;
                    }
            	} 
            }
            if (results[0].equals(CSCConstants.FAIL_RESULT)) {
                break;
            }
        }
        return results;
    }

    *//**
     * 
     * 功能描述: <br>
     * 返回excel报错结果
     * 
     * @param row 行数
     * @param column 列数
     * @param value 值
     * @param msg 错误信息
     * @return result 结果
     * 
     *//*
    public static String getErrorMsgForExcel(int row, int column, String value, String msg) {
        String result = "";
        StringBuffer resultTempBuffer = new StringBuffer();
        if (row > 0) {
            resultTempBuffer.append("第" + row + "行,");
        }
        if (column >= 0) {
            resultTempBuffer.append("第" + (column + 1) + "列,");
        }
        if (!StringUtil.isEmpty(value)) {
            resultTempBuffer.append("内容:" + value + ",");
        }
        if (!StringUtil.isEmpty(msg)) {
            resultTempBuffer.append(msg);
        }
        result = resultTempBuffer.toString();
        return result;
    }

    *//**
     * 
     * 功能描述: <br>
     * 获取某单元的值
     * 
     * @param row 行数
     * @param columnNum 列数
     * @return cellValue 单元值
     *//*
    public static String getRowValue(Row row, int columnNum) {
        Cell cell = row.getCell(columnNum);
        String cellValue = "";
        if (null != cell) {
            // 以下是判断数据的类型
            switch (cell.getCellType()) {
            // XSSFCell可以达到相同的效果
            *//**
             * 数字
             *//*
                case XSSFCell.CELL_TYPE_NUMERIC:
                    double d = cell.getNumericCellValue();
                    cellValue = d + "";
                    if (cellValue.endsWith(".0")) {
                        cellValue = "" + (int) Double.parseDouble(cellValue);
                    }
                    break;
                case XSSFCell.CELL_TYPE_STRING: // 字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case XSSFCell.CELL_TYPE_FORMULA: // 公式
                    cellValue = cell.getCellFormula() + "";
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
        return cellValue;
    }

    *//**
     * 
     * 功能描述: <br>
     * 获取excel对象
     * 
     * @param excelFileInputStream 文件流
     * @return wb excel对象
     *//*
    public static Workbook getExcelFileInputStream(FileInputStream excelFileInputStream) {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(excelFileInputStream);
        } catch (InvalidFormatException e) {
            logger.error(exceptionMsg, e);
            throw new ReadExcelException(CSCErrorCode.READ_EXCEL_ERR_CODE, CSCErrorMsg.READ_EXCEL_ERR_INFO, e);
        } catch (IOException e) {
            logger.error(exceptionMsg, e);
            throw new ReadExcelException(CSCErrorCode.READ_EXCEL_ERR_CODE, CSCErrorMsg.READ_EXCEL_ERR_INFO, e);

        } finally {
            if (excelFileInputStream != null) {
                try {
                    excelFileInputStream.close();
                } catch (IOException e) {
                    logger.error(exceptionMsg, e);
                    throw new ReadExcelException(CSCErrorCode.READ_EXCEL_ERR_CODE, CSCErrorMsg.READ_EXCEL_ERR_INFO, e);
                }
            }
        }
        return wb;
    }
    *//**
     * 创建excel对象，并导入数据
     * 
     * *//*
    public static HSSFWorkbook writeExcel(List<List<Object>> data){
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet();
        //get rowcounts
        int cellSize=0;
        if(data!=null&&data.size()>0){
            int rowSize=data.size();
            //init columns
            List<Object> dataList=null;
            for(int i=0;i<rowSize;i++){
                HSSFRow row=sheet.createRow((short)i);
                dataList=data.get(i);
                //get columns
                if(dataList!=null){
                    cellSize=dataList.size();
                }
                for(int j=0;j<cellSize;j++){
                    Object cellValue=dataList.get(j);
                    HSSFCell cell=row.createCell((short)j);
                    setCell(cell,cellValue);
                }
            }
        }
        //set column auto width
        for(int k=0;k<cellSize;k++){
            sheet.autoSizeColumn((short)k+3);
        }
        return workbook;
    }
    *//**
     * 给单元内容赋值
     * *//*
    private static void setCell(HSSFCell cell,Object value){
        if(value instanceof Integer){
            cell.setCellValue(((Integer)value).intValue());
        }else if(value instanceof String){
            cell.setCellValue((String)value);
        }else if(value instanceof Double){
            cell.setCellValue(((Double)value).doubleValue());
        }else if(value instanceof Float){
            cell.setCellValue(((Float)value).floatValue());
        }else if(value instanceof Long){
            cell.setCellValue(((Long)value).longValue());
        }else if(value instanceof Boolean){
            cell.setCellValue(((Boolean)value).booleanValue());
        }else if(value ==null){
            cell.setCellValue("");
        }else{
            Date d=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cell.setCellValue(sdf.format(value));
        }
    }
    
}
*/