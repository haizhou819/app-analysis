package com.iflytek.xfyun.service.prehandle.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iflytek.xfyun.dao.AppBizDao;
import com.iflytek.xfyun.dao.AppRetentDao;
import com.iflytek.xfyun.dto.AppActiveDegree;
import com.iflytek.xfyun.dto.AppBizInfo;
import com.iflytek.xfyun.dto.AppBizInfoMonthly;
import com.iflytek.xfyun.dto.AppRetentInfoMonthly;
import com.iflytek.xfyun.dynamicdb.DataSourceContextHolder;
import com.iflytek.xfyun.dynamicdb.DataSources;
import com.iflytek.xfyun.service.prehandle.IModelPreHandle;
import com.iflytek.xfyun.utils.ArithUtil;
@Service
public class ModelPreHandleImpl implements IModelPreHandle{
	@Resource
	private AppBizDao appBizDao;
	@Resource
	private AppRetentDao retentDao;
	
	@Override
	public List<AppActiveDegree> mergeAndConvert(Map<String, Object> params,List<String> appIdList) {
		List<AppBizInfo> appBizInfoList = mergetest(params,appIdList);
		List<AppActiveDegree> appActiveDegreeList = convert(appBizInfoList);
		return appActiveDegreeList;
	}
	
	@Override
	public List<AppActiveDegree> mergeAndConvert(Map<String, Object> params) {
		List<AppBizInfo> appBizInfoList = merge(params);
		List<AppActiveDegree> appActiveDegreeList = convert(appBizInfoList);
		return appActiveDegreeList;
	}
	
	public List<AppBizInfo> mergetest(Map<String, Object> params,List<String> appIdList){
		DataSourceContextHolder.setDbType(DataSources.DS2);
		String dateStr = (String)params.get("month");//2017-02-01,默认传入选择月份的第一天
		String ctimestamp = getFirstDayOfMonth(dateStr);
		String ltimestamp = getFirstDayOfPreMonth(dateStr);
		
        params.put("ctimestamp", ctimestamp);
		params.put("ltimestamp", ltimestamp);
		Map<String,AppBizInfoMonthly> appBizMap = new HashMap<String,AppBizInfoMonthly>();
		for(String appId : appIdList){
			params.put("appId",appId);
			AppBizInfoMonthly appBizInfoMonthly = appBizDao.getAppBizInfoMonthly(params);
			if(appBizInfoMonthly != null){
				appBizMap.put(appId, appBizInfoMonthly);
			}
		}
		
		ctimestamp = getLastDayOfMonth(dateStr);
		ltimestamp = getLastDayOfPreMonth(dateStr);
		Map<String,AppRetentInfoMonthly> appRetentMap = new HashMap<String,AppRetentInfoMonthly>();
		params.put("type", "MONTH");
		params.put("ctimestamp", ctimestamp);
		params.put("ltimestamp", ltimestamp);
		//params.put("appids", appIdList);
		
		DataSourceContextHolder.setDbType(DataSources.DS3);
		List<AppBizInfo> appBizInfoList = new ArrayList<AppBizInfo>();
		for(String appId : appBizMap.keySet()){
			params.put("appId", appId);
			AppRetentInfoMonthly appRetentInfoMonthly = retentDao.getAppRetentInfoMonthly(params);
			if(appRetentInfoMonthly != null){
				appRetentMap.put(appId, appRetentInfoMonthly);
				AppBizInfo appBizInfo = new AppBizInfo();
				appBizInfo.setAppId(appId);
				appBizInfo.setAppBizInfoMonthly(appBizMap.get(appId));
				appBizInfo.setAppRetentInfoMonthly(appRetentInfoMonthly);
				appBizInfoList.add(appBizInfo);
			}
		}
		System.out.println("appBizInfoMonthly size:"+appBizMap.size());
		System.out.println("appBizInfoList size:"+appBizInfoList.size());
		return appBizInfoList;
	}
	
	//将app的业务数据经过一定的就算，得到相关增长率和留存率
	public List<AppActiveDegree> convert(List<AppBizInfo> appBizInfoList){
		List<AppActiveDegree> appActiveDegreeList = new ArrayList<AppActiveDegree>();
		for(AppBizInfo appBizInfo : appBizInfoList){
			AppBizInfoMonthly appBizInfoMonthly = appBizInfo.getAppBizInfoMonthly();
			AppRetentInfoMonthly appRetentInfoMonthly = appBizInfo.getAppRetentInfoMonthly();
			double relativeGrowthRate = 0;//新增注册用户增长率
			double growthRate =  0;//新增月活用户增长率
			double retentRate = 0;//1个月留存率
			double currentNewRegUsers = appBizInfoMonthly.getCurrentNewRegUsers();
			double lastNewRegUsers = appBizInfoMonthly.getLastNewRegUsers();
			double currentUsers = appBizInfoMonthly.getCurrentUsers();
			double lastUsers = appBizInfoMonthly.getLastUsers();
			double retent1 = appRetentInfoMonthly.getRetent1();
			double lastActiveUsers = appRetentInfoMonthly.getLastActiveUsers();
			if(appBizInfoMonthly.getLastNewRegUsers() == 0){
				relativeGrowthRate = 100;
			}else{
				relativeGrowthRate = ArithUtil.div((ArithUtil.sub(currentNewRegUsers, lastNewRegUsers)),lastNewRegUsers,3);
			}
			if(appBizInfoMonthly.getLastUsers() == 0){
				growthRate = 100;
			}else{
				growthRate = ArithUtil.div((ArithUtil.sub(currentUsers, lastUsers)),lastUsers,3);
			}
			if(appRetentInfoMonthly.getLastActiveUsers() == 0){
				retentRate = 100;
			}else{
				retentRate = ArithUtil.div(retent1, lastActiveUsers, 3);
			}
			AppActiveDegree appActiveDegree = new AppActiveDegree();
			appActiveDegree.setActiveMonthly(appBizInfoMonthly.getCurrentUsers());
			appActiveDegree.setAppId(appBizInfo.getAppId());
			appActiveDegree.setNewRegUsersRateMonthly(relativeGrowthRate);
			appActiveDegree.setUsersRateMonthly(growthRate);
			appActiveDegree.setRetentRateMonthly(retentRate);
			appActiveDegree.setServiceCountMonthly(appBizInfoMonthly.getServiceCount());
			appActiveDegree.setTimestamp(appBizInfoMonthly.getTimestamp());
			appActiveDegreeList.add(appActiveDegree);
		}
		return appActiveDegreeList;
	}
	
	public List<AppBizInfo> merge(Map<String, Object> params){
		//根据月份信息查找需要分析的app数据
		DataSourceContextHolder.setDbType(DataSources.DS2);
		String dateStr = (String)params.get("month");//
		String ctimestamp = getFirstDayOfMonth(dateStr);
		String ltimestamp = getFirstDayOfPreMonth(dateStr);
		params.put("ctimestamp", ctimestamp);
		params.put("ltimestamp", ltimestamp);
		List<AppBizInfoMonthly> appBizInfoMonthlyList = appBizDao.getAppBizInfoMonthlyList(params);
		List<String> appIdList = new ArrayList<String>();
		Map<String,AppBizInfoMonthly> appBizMap = new HashMap<String,AppBizInfoMonthly>();
		for(AppBizInfoMonthly appBizInfoMonthly : appBizInfoMonthlyList){
			appIdList.add(appBizInfoMonthly.getAppId());
			appBizMap.put(appBizInfoMonthly.getAppId(), appBizInfoMonthly);
		}
		//查找app的上个月的留存数据
		DataSourceContextHolder.setDbType(DataSources.DS3);
		ctimestamp = getLastDayOfMonth(dateStr);
		ltimestamp = getLastDayOfPreMonth(dateStr);
		params.put("type", "MONTH");
		params.put("ctimestamp", ctimestamp);
		params.put("ltimestamp", ltimestamp);
		params.put("appids", appIdList);
		List<AppRetentInfoMonthly> appRetentInfoMonthlyList = retentDao.getAppRetentInfoMonthlyByIds(params);
		List<AppBizInfo> appBizInfoList = new ArrayList<AppBizInfo>();
		//将app的月活等业务数据和app的留存数据合并为一个对象
		for(AppRetentInfoMonthly appRetentInfoMonthly : appRetentInfoMonthlyList){
			String appId = appRetentInfoMonthly.getAppId();
			AppBizInfo appBizInfo = new AppBizInfo();
			appBizInfo.setAppId(appId);
			appBizInfo.setAppRetentInfoMonthly(appRetentInfoMonthly);
			appBizInfo.setAppBizInfoMonthly(appBizMap.get(appId));
			
			appBizInfoList.add(appBizInfo);
		}
		return appBizInfoList;
	}
	
	//获取月份第一天
	public String getFirstDayOfMonth(String date){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd"); //格式化日期
		String firstDay = "";
		Date dateTime;
		try {
			dateTime = dft.parse(date);
			Calendar calendar = new GregorianCalendar();
	        calendar.setTime(dateTime);
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
	        firstDay = dft.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return firstDay;
	}
	//获取月份最后一天
	public String getLastDayOfMonth(String date){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd"); //格式化日期
		String lastDay = "";
		Date dateTime;
		try {
			dateTime = dft.parse(date);
			Calendar calendar = new GregorianCalendar();
	        calendar.setTime(dateTime);
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	        lastDay = dft.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return lastDay;
	}
	//获取上个月第一天
	public String getFirstDayOfPreMonth(String date){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd"); //格式化日期
		String firstDay = "";
		Date dateTime;
		try {
			dateTime = dft.parse(date);
			Calendar calendar = new GregorianCalendar();
	        calendar.setTime(dateTime);
	        calendar.add(Calendar.MONTH, -1);
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
	        firstDay = dft.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return firstDay;
	}
	//获取上个月最后一天
	public String getLastDayOfPreMonth(String date){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd"); //格式化日期
		String lastDay = "";
		Date dateTime;
		try {
			dateTime = dft.parse(date);
			Calendar calendar = new GregorianCalendar();
	        calendar.setTime(dateTime);
	        calendar.add(Calendar.MONTH, -1);
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	        lastDay = dft.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return lastDay;
	}

	
}
