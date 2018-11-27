package com.iflytek.xfyun.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 
* @ClassName:ExcelUtil 
* @Description:excel工具类 
* @author:hzyuan@iflytek.com 
* @date:2017年3月28日
* 
* @param <E>
 */
public class ExcelUtil<E> {
	/**
	 * 
	 * @MethodName:exportToExcel   
	 * @Description:导出数据到excel，文件类型xlsx
	 * @param headerTitles 表格标题
	 * @param dataset 待导出的数据
	 * @param fileName excel文件名
	 * @param filePath excel文件地址
	 * @return:void      
	 * @throws
	 */
	public static <E> void exportToExcel(String[] headerTitles,Collection<E> dataset, String fileName,String filePath){
		// 创建工作薄  
        XSSFWorkbook workbook = new XSSFWorkbook();  
        // 创建工作表  
        XSSFSheet sheet = workbook.createSheet(fileName); 
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 15);
        // 设置字体样式
        XSSFFont  fontStyle = workbook.createFont();
        fontStyle.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//设置粗体
        // 设置首行标题单元格样式
        XSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        //将字体设置到单元格样式
        titleCellStyle.setFont(fontStyle);
        //设置主体数据单元格样式
        XSSFCellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        
        // 产生表格标题行  
        XSSFRow row = sheet.createRow(0); 
        for (short i = 0; i < headerTitles.length; i++) {  
            XSSFCell cell = row.createCell(i);  
            XSSFRichTextString text = new XSSFRichTextString(headerTitles[i]);  
            cell.setCellValue(text); 
            // 将标题单元格样式应用于标题单元格
            cell.setCellStyle(titleCellStyle);
        }  
        try {  
            // 遍历集合数据，产生数据行  
            Iterator<E> it = dataset.iterator();  
            int index = 0;  
            while (it.hasNext()) {  
                index++;  
                row = sheet.createRow(index);  
                E e = (E) it.next();  
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = e.getClass().getDeclaredFields();  
                for (short i = 0; i < headerTitles.length; i++) {  
                    XSSFCell cell = row.createCell(i);  
                    Field field = fields[i];  
                    String fieldName = field.getName();  
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);  
                    Class<? extends Object> clazz = e.getClass();  
                    Method getMethod = clazz.getMethod(getMethodName, new Class[] {});  
                    Object value = getMethod.invoke(e, new Object[] {});  
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;  
                    // 其它数据类型都当作字符串简单处理  
                    if(value != null && value != ""){  
                        textValue = value.toString();  
                    }  
                    if (textValue != null) {  
                        XSSFRichTextString richString = new XSSFRichTextString(textValue);  
                        cell.setCellValue(richString);  
                        // 将主体数据单元格样式应用于数据单元格
                        cell.setCellStyle(dataCellStyle);
                    }  
                }  
            }  
            exportToFile(workbook, fileName,filePath);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
	}
	
	public static void exportToFile(XSSFWorkbook workbook, String fileName,String filePath){
		FileOutputStream fos = null;  
        try {  
        	File fileDir = new File(filePath);
        	if(!fileDir.exists()){
        		fileDir.mkdirs();
        	}
        	File file = new File(filePath + fileName);
        	if(!file.exists()){
        		file.createNewFile();
        	}
            fos = new FileOutputStream(file);  
            workbook.write(fos);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fos != null) {  
                try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
            }  
        }  
	}
	
	public static <E> List<E> importFromExcel(){
		return null;
	}
}
