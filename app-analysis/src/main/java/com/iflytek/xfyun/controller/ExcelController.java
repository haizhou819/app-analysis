package com.iflytek.xfyun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
* @ClassName:ExcelController 
* @Description:excel对外controller接口 
* @author:hzyuan@iflytek.com 
* @date:2017年3月29日
*
 */
@Controller
@RequestMapping(value="/excel")
public class ExcelController {
	private static Logger logger = LoggerFactory.getLogger(ExcelController.class);
//	@Autowired
//	private ICalculateRuleService calculateRuleService;
//	@Autowired
//	private IExcelDisplayService excelDisplayService;
	
	@RequestMapping(value = {"/appdata"}, method = {RequestMethod.GET})
	public void exportAppData(HttpServletRequest request,HttpServletResponse response){
		logger.debug("--------开始---------");
		
		logger.debug("---------结束---------");
	}
}
