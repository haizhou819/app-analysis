package com.iflytek.xfyun.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.iflytek.xfyun.dao.AppBizDao;
import com.iflytek.xfyun.dto.AppActiveDegree;
import com.iflytek.xfyun.dto.AppBizInfoMonthly;


@Repository
public class AppBizDaoImpl  extends SqlSessionDaoSupport implements AppBizDao{
	@Override
	public void saveAppActiveData(AppActiveDegree appActiveDegree) {
		this.getSqlSession().insert("AppActiveDegreeMapper.saveAppActiveData", appActiveDegree);
	}
	
	@Override
	public List<AppActiveDegree> getAppActiveInfo(Map<String, Object> params) {
		List<AppActiveDegree> appActiveDegreeList = this.getSqlSession().selectList("AppActiveDegreeMapper.getAppActiveInfo", params);
		return appActiveDegreeList;
	}
	
	@Override
	public void updateAppActiveData(AppActiveDegree appActiveDegree) {
		this.getSqlSession().update("AppActiveDegreeMapper.updateAppActiveInfo", appActiveDegree);
	}
	
	@Override
	public AppBizInfoMonthly getAppBizInfoMonthly(Map<String, Object> params) {
		AppBizInfoMonthly appBizInfoMonthly = this.getSqlSession().selectOne("AppBizInfoMonthlyMapper.getAppBizInfo", params);
		return appBizInfoMonthly;
	}
	
	@Override
	public List<AppBizInfoMonthly> getAppBizInfoMonthlyList(Map<String, Object> params) {
		List<AppBizInfoMonthly> appBizInfoMonthly = this.getSqlSession().selectList("AppBizInfoMonthlyMapper.getAppBizInfoList", params);
		return appBizInfoMonthly;
	}
}
