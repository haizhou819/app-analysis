package com.iflytek.xfyun.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.iflytek.xfyun.dto.AppActiveDegree;
import com.iflytek.xfyun.service.display.impl.ExcelDisplayServiceImpl;
import com.iflytek.xfyun.service.name.impl.AppNameServiceImpl;
import com.iflytek.xfyun.service.prehandle.impl.ModelPreHandleImpl;
import com.iflytek.xfyun.service.rule.impl.CalaulateRuleServiceImpl;
import com.iflytek.xfyun.utils.ConfigUtil;
@Component
public class Client { 
//	@Autowired
//	private AppActiveDao appActiveDao;
	@Autowired
	private ExcelDisplayServiceImpl excelDisplayServiceImpl;
	@Autowired
	private ModelPreHandleImpl modelPreHandleImpl;
	@Autowired
	private AppNameServiceImpl appNameServiceImpl;
	@Autowired
	private CalaulateRuleServiceImpl calaulateRuleServiceImpl;
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Client client = (Client)ctx.getBean("client");
		client.testNameService();
	}
	
	public void testNameService(){
		String dateStr = ConfigUtil.getValue("date");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("month", dateStr);
		List<AppActiveDegree> appActiveDegreeList1 = modelPreHandleImpl.mergeAndConvert(params);
		//List<AppActiveDegree> appActiveDegreeList1 = modelPreHandleImpl.mergeAndConvert(params,appIdList);
		List<AppActiveDegree> appActiveDegreeList2 = appNameServiceImpl.getAppNameFromUser(appActiveDegreeList1);
		List<AppActiveDegree> appActiveDegreeList3 = calaulateRuleServiceImpl.getAppSore(appActiveDegreeList2);
		Collections.sort(appActiveDegreeList3);
		//数据入库
		//DataSourceContextHolder.setDbType(DataSources.DS1);
		//appActiveDao.saveAppActiveInfos(appActiveDegreeList3);
		excelDisplayServiceImpl.displayAppDataByExcel(appActiveDegreeList3);
	}
}
