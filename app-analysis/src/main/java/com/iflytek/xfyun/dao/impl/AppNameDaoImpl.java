package com.iflytek.xfyun.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.iflytek.xfyun.dao.AppNameDao;
import com.iflytek.xfyun.dto.AppCompanyInfo;
import com.iflytek.xfyun.dto.AppNameInfo;
@Repository
public class AppNameDaoImpl extends SqlSessionDaoSupport implements AppNameDao{
	@Override
	public List<AppNameInfo> getAppNameFromReleaseByIds(List<String> appIds) {
		List<AppNameInfo> appReleaseList = this.getSqlSession().selectList("AppNameInfoMapper.getAppNameFromReleaseByIds", appIds);
		return appReleaseList;
	}

	@Override
	public List<AppNameInfo> getAppNameFromUserByIds(List<String> appIds) {
		List<AppNameInfo> appReleaseList = this.getSqlSession().selectList("AppNameInfoMapper.getAppNameFromUserByIds", appIds);
		return appReleaseList;
	}

	@Override
	public List<AppCompanyInfo> getAppCompanyNameFromCertificateByUids(List<String> uids) {
		List<AppCompanyInfo> appCompanyInfoList = this.getSqlSession().selectList("AppNameInfoMapper.getAppCompanyNameFromCertificate", uids);
		return appCompanyInfoList;
	}

	@Override
	public List<AppCompanyInfo> getAppCompanyNameFromUserByUids(List<String> uids) {
		List<AppCompanyInfo> appCompanyInfoList = this.getSqlSession().selectList("AppNameInfoMapper.getAppCompanyNameFromUser", uids);
		return appCompanyInfoList;
	}
}
