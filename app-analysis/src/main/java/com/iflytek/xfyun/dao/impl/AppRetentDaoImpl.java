package com.iflytek.xfyun.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.iflytek.xfyun.dao.AppRetentDao;
import com.iflytek.xfyun.dto.AppRetentInfoMonthly;
@Repository
public class AppRetentDaoImpl extends SqlSessionDaoSupport implements AppRetentDao{
	@Override
	public AppRetentInfoMonthly getAppRetentInfoMonthly(Map<String, Object> params) {
		AppRetentInfoMonthly appRetentInfoMonthly = this.getSqlSession().selectOne("AppRetentInfoMonthlyMapper.getAppRetentInfo", params);
		return appRetentInfoMonthly;
	}
	
	@Override
	public List<AppRetentInfoMonthly> getAppRetentInfoMonthlyByIds(Map<String, Object> param) {
		List<AppRetentInfoMonthly> appRetentInfoMonthlyList = this.getSqlSession().selectList("AppRetentInfoMonthlyMapper.getAppRetentInfoListByIds", param);
		return appRetentInfoMonthlyList;
	}
}
