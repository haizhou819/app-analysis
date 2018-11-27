package com.iflytek.xfyun.service.name.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iflytek.xfyun.dao.AppNameDao;
import com.iflytek.xfyun.dto.AppActiveDegree;
import com.iflytek.xfyun.dto.AppCompanyInfo;
import com.iflytek.xfyun.dto.AppNameInfo;
import com.iflytek.xfyun.dynamicdb.DataSourceContextHolder;
import com.iflytek.xfyun.dynamicdb.DataSources;
import com.iflytek.xfyun.service.name.IAppNameService;
@Service
public class AppNameServiceImpl implements IAppNameService{
	@Autowired
	private AppNameDao appNameDao; 
	@Override
	public List<AppActiveDegree> getAppNameFromUser(List<AppActiveDegree> appActiveDegreeList) {
		List<String> appIdList = new ArrayList<String>();
		Map<String,AppActiveDegree> appDegreeMap = new HashMap<String,AppActiveDegree>();
		for(AppActiveDegree appActiveDegree : appActiveDegreeList){
			appIdList.add(appActiveDegree.getAppId());
			appDegreeMap.put(appActiveDegree.getAppId(), appActiveDegree);
		}
		
		DataSourceContextHolder.setDbType(DataSources.DS6);
		List<AppNameInfo> appNameList = appNameDao.getAppNameFromUserByIds(appIdList);
		List<AppNameInfo> afterHadleAppNameList  = handleAppNameInfo(appNameList);//添加company信息
		for(AppNameInfo AppNameInfo : afterHadleAppNameList){
			AppActiveDegree appActiveDegree = appDegreeMap.get(AppNameInfo.getAppId());
			appActiveDegree.setAppName(AppNameInfo.getAppName());
			appActiveDegree.setPlatform(AppNameInfo.getPlatform());
			appActiveDegree.setCompany(AppNameInfo.getCompany());
		}
		
		//过滤没有appname的数据
		List<AppActiveDegree> activeDegreeList = new ArrayList<>();
		for(AppActiveDegree appActiveDegree : appDegreeMap.values()){
			if(StringUtils.isNotEmpty(appActiveDegree.getAppName())){
				activeDegreeList.add(appActiveDegree);
			}
		}
		
		//JDK8 stream api 过滤没有appname的数据
		/*return appDegreeMap.values()
						.stream()
						.filter(appActiveDegree->StringUtils.isNotEmpty(appActiveDegree.getAppName()))
						.collect(Collectors.toList());*/
		return activeDegreeList;
	}

	@Override
	public List<AppActiveDegree> getAppNameFromRelease(List<AppActiveDegree> appActiveDegreeList) {
		return null;
	}

	@Override
	public List<AppNameInfo> getAppCompanyNameFromCertificate(List<AppNameInfo> appNameInfoList) {
		List<String> appUidList = new ArrayList<String>();
		Map<String,AppNameInfo> appNameMap = new HashMap<String,AppNameInfo>();
		for(AppNameInfo appNameInfo : appNameInfoList){
			appUidList.add(appNameInfo.getUid());
			appNameMap.put(appNameInfo.getUid(), appNameInfo);
		}
		DataSourceContextHolder.setDbType(DataSources.DS4);
		List<AppCompanyInfo> appCompanyInfos = appNameDao.getAppCompanyNameFromCertificateByUids(appUidList);
		for(AppCompanyInfo appCompany : appCompanyInfos){
			AppNameInfo appNameInfo = appNameMap.get(appCompany.getUid());
			appNameInfo.setCompany(appCompany.getCompany());
		}
		return appNameInfoList;
	}

	@Override
	public List<AppNameInfo> getAppCompanyNameFromUser(List<AppNameInfo> appNameInfoList) {
		return null;
	}

	@Override
	public List<AppNameInfo> handleAppNameInfo(List<AppNameInfo> appNameInfoList) {
		//根据uid获取app company
		List<String> appUidList = new ArrayList<String>();
		Map<String,AppNameInfo> appNameMap = new HashMap<String,AppNameInfo>();
		for(AppNameInfo appNameInfo : appNameInfoList){
			appUidList.add(appNameInfo.getUid());
			appNameMap.put(appNameInfo.getUid(), appNameInfo);
		}
		//根据uid从certificate表取app company信息
		DataSourceContextHolder.setDbType(DataSources.DS4);
		List<String> uids = new ArrayList<>();
		List<AppCompanyInfo> appCompanyInfos = appNameDao.getAppCompanyNameFromCertificateByUids(appUidList);
		for(AppCompanyInfo appCompany : appCompanyInfos){
			AppNameInfo appNameInfo = appNameMap.get(appCompany.getUid());
			appNameInfo.setCompany(appCompany.getCompany());
			uids.add(appCompany.getUid());
		}
		
		//根据uid从user_extends_info表取剩余没有取到app company的app数据
		DataSourceContextHolder.setDbType(DataSources.DS6);
		//List<String> restUids = appCompanyInfos.stream().map(obj->obj.getUid()).collect(Collectors.toList());
		boolean isSuccess = appUidList.removeAll(uids);
		if(isSuccess){
			List<AppCompanyInfo> restAppCompanyInfos = appNameDao.getAppCompanyNameFromUserByUids(appUidList);
			for(AppCompanyInfo appCompany : restAppCompanyInfos){
				AppNameInfo appNameInfo = appNameMap.get(appCompany.getUid());
				appNameInfo.setCompany(appCompany.getCompany());
			}
		}
		return appNameInfoList;
	}
}
