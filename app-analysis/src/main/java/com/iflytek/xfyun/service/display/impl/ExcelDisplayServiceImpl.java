package com.iflytek.xfyun.service.display.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.iflytek.xfyun.dto.AppActiveDegree;
import com.iflytek.xfyun.service.display.IExcelDisplayService;
import com.iflytek.xfyun.utils.ConfigUtil;
import com.iflytek.xfyun.utils.ExcelUtil;
/**
 * 
* @ClassName:ExcelDisplayServiceImpl 
* @Description:将平台app的相关数据，经过计算以excel形式展现 
* @author:hzyuan@iflytek.com 
* @date:2017年3月27日
*
 */
@Service
public class ExcelDisplayServiceImpl implements IExcelDisplayService{
	@Override
	public void displayAppDataByExcel(List<AppActiveDegree> appActiveDegreeList) {
		String fileName = ConfigUtil.getValue("filename");
		String filepath = ConfigUtil.getValue("filepath");
		String[] headers = new String[]{"appId","app名称","平台","月活","月服务量","月新增注册用户增长率","月新增活跃用户增长率","月活跃用户留存率",
										"月活得分","服务量得分","新增注册用户得分","新增活跃用户得分","活跃用户留存得分","所属公司","总得分"};
		ExcelUtil.exportToExcel(headers, appActiveDegreeList, fileName, filepath);
	}
	
	public static void main(String[] args) {
//		ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext.xml");
//		
//		ExcelDisplayServiceImpl edsi = ctx.getBean(ExcelDisplayServiceImpl.class);
//		Map<String,Object> params = new HashMap<String,Object>();
//		edsi.displayAppDataByExcel(params);
	}

}
