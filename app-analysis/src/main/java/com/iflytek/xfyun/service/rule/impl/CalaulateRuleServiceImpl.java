package com.iflytek.xfyun.service.rule.impl;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iflytek.xfyun.dto.AppActiveDegree;
import com.iflytek.xfyun.service.rule.ICalculateRuleService;
import com.iflytek.xfyun.utils.ArithUtil;
import com.iflytek.xfyun.utils.ConfigUtil;
/**
 * 
* @ClassName:CalaulateRuleServiceImpl 
* @Description:根据指定规则计算app应用的得分 
* @author:hzyuan@iflytek.com 
* @date:2017年3月27日
*
 */
@Service
public class CalaulateRuleServiceImpl implements ICalculateRuleService{
	private static Logger logger = LoggerFactory.getLogger(CalaulateRuleServiceImpl.class);
	
	@Override
	public List<AppActiveDegree> getAppSore(List<AppActiveDegree> appActiveDegreeList) {
		logger.info("根据指定规则统计app得分开始");
		for(AppActiveDegree appActiveDegree : appActiveDegreeList){
			double score = 0;
			double monthActivePriority = Double.parseDouble(ConfigUtil.getValue("activeMonthlyPriority"));//月活权重
			double serviceCountMonthlyPriority = Double.parseDouble(ConfigUtil.getValue("serviceCountMonthlyPriority"));//月服务量权重
			double newRegUsersRateMonthlyPriority = Double.parseDouble(ConfigUtil.getValue("newRegUsersRateMonthlyPriority"));//月新增注册用户增长率权重
			double usersRateMonthlyPriority = Double.parseDouble(ConfigUtil.getValue("usersRateMonthlyPriority"));//月新增活跃用户增长率权重
			double retentRateMonthlyPriority = Double.parseDouble(ConfigUtil.getValue("retentRateMonthlyPriority"));//月活跃用户留存率权重
			
			int activeMonthly = appActiveDegree.getActiveMonthly();//月活
			long serviceCountMonthly = appActiveDegree.getServiceCountMonthly();//月服务量
			double newRegUsersRateMonthly = appActiveDegree.getNewRegUsersRateMonthly();//月新增注册用户增长率
			double usersRateMonthly = appActiveDegree.getUsersRateMonthly();//月新增活跃用户增长率
			double retentRateMonthly = appActiveDegree.getRetentRateMonthly();//月活跃用户留存率
			
			int activeMonthlyScore = getActiveMonthlyScore(activeMonthly);
			int serviceCountScore = getServiceCountScore(serviceCountMonthly);
			int incRegUsersScore = getIncUsersScore(newRegUsersRateMonthly);
			int incActUsersScore = getIncUsersScore(usersRateMonthly);
			int retentUsersScore = getRetentUsersScore(retentRateMonthly);
			
			double finalActiveMonthlyScore = ArithUtil.mul(activeMonthlyScore, monthActivePriority);
			double finalServiceCountScore = ArithUtil.mul(serviceCountScore, serviceCountMonthlyPriority);
			double finalIncRegUsersScore = ArithUtil.mul(incRegUsersScore, newRegUsersRateMonthlyPriority);
			double finalIncActUsersScore = ArithUtil.mul(incActUsersScore, usersRateMonthlyPriority);
			double finalRetentUsersScore = ArithUtil.mul(retentUsersScore, retentRateMonthlyPriority);

			score = finalActiveMonthlyScore+finalServiceCountScore+finalIncRegUsersScore+finalIncActUsersScore+finalRetentUsersScore;
			appActiveDegree.setActiveMonthlyScore(finalActiveMonthlyScore);
			appActiveDegree.setServiceCountScore(finalServiceCountScore);
			appActiveDegree.setIncRegUsersScore(finalIncRegUsersScore);
			appActiveDegree.setIncActUsersScore(finalIncActUsersScore);
			appActiveDegree.setRetentUsersScore(finalRetentUsersScore);
			appActiveDegree.setScore(score);
		}
		logger.info("根据指定规则统计app得分结束，共统计{}个app得分记录",appActiveDegreeList.size());
		return appActiveDegreeList;
	}
	
	public int getActiveMonthlyScore(int activeMonthly){
		if(activeMonthly > 1000*100*100){//大于等于1000万
			return 100;
		}else if(activeMonthly > 100*100*100 && activeMonthly <= 1000*100*100){//大于等于100万，小于1000万
			return 80;
		}else if(activeMonthly > 50*100*100 && activeMonthly <= 100*100*100){//大于等于50万，小于100万
			return 60;
		}else if(activeMonthly > 10*100*100 && activeMonthly <= 50*100*100){//大于等于10万，小于50万
			return 40;
		}else if(activeMonthly > 1*100*100 && activeMonthly <= 10*100*100){//大于等于1万，小于10万
			return 20;
		}else if(activeMonthly > 500 && activeMonthly <= 1*100*100){//大于等于500，小于1万
			return 0;
		}
		return 0;
	}
	
	public int getServiceCountScore(long serviceCountMonthly){
		if(serviceCountMonthly > 100*100*100*100){//大于等于1亿
			return 100;
		}else if(serviceCountMonthly > 1000*100*100 && serviceCountMonthly <= 100*100*100*100){//大于等于1000万，小于1亿
			return 80;
		}else if(serviceCountMonthly > 100*100*100 && serviceCountMonthly <= 1000*100*100){//大于等于100万，小于1000万
			return 60;
		}else if(serviceCountMonthly > 10*100*100 && serviceCountMonthly <= 100*100*100){//大于等于10万，小于100万
			return 40;
		}else if(serviceCountMonthly > 1*100*100 && serviceCountMonthly <= 10*100*100){//大于等于1万，小于10万
			return 20;
		}else if(serviceCountMonthly > 0 && serviceCountMonthly <= 1*100*100){//大于等于0，小于1万
			return 0;
		}
		return 0;
	}
	
	public int getIncUsersScore(double incUsersRate){
		if(incUsersRate > 10){
			return 100;
		}else if(incUsersRate > 1 && incUsersRate <= 10){
			return 80;
		}else if(incUsersRate > 0.5 && incUsersRate <= 1){
			return 60;
		}else if(incUsersRate > 0 && incUsersRate <= 0.5){
			return 40;
		}else if(incUsersRate > -0.5 && incUsersRate <= 0){
			return 20;
		}else if(incUsersRate > -1 && incUsersRate <= -0.5){
			return 0;
		}
		return 0;
	}
	
	public int getRetentUsersScore(double retentRate){
		if(retentRate > 0.8 && retentRate <= 1){
			return 100;
		}else if(retentRate > 0.6 && retentRate <= 0.8){
			return 80;
		}else if(retentRate > 0.4 && retentRate <= 0.6){
			return 60;
		}else if(retentRate > 0.2 && retentRate <= 0.4){
			return 40;
		}else if(retentRate > 0.1 && retentRate <= 0.2){
			return 20;
		}else if(retentRate > 0 && retentRate <= 0.1){
			return 0;
		}
		return 0;
	}
}
