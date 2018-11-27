package com.iflytek.xfyun.dao;

import java.util.List;

import com.iflytek.xfyun.dto.AppCompanyInfo;
import com.iflytek.xfyun.dto.AppNameInfo;

public interface AppNameDao {
	List<AppNameInfo> getAppNameFromReleaseByIds(List<String> appIds);
	List<AppNameInfo> getAppNameFromUserByIds(List<String> appIds);
	List<AppCompanyInfo> getAppCompanyNameFromCertificateByUids(List<String> uids);
	List<AppCompanyInfo> getAppCompanyNameFromUserByUids(List<String> uids);
}
