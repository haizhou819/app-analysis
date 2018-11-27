package com.iflytek.xfyun.service.name;

import java.util.List;

import com.iflytek.xfyun.dto.AppActiveDegree;
import com.iflytek.xfyun.dto.AppNameInfo;

public interface IAppNameService {
	List<AppActiveDegree> getAppNameFromUser(List<AppActiveDegree> appActiveDegreeList);
	List<AppActiveDegree> getAppNameFromRelease(List<AppActiveDegree> appActiveDegreeList);
	List<AppNameInfo> getAppCompanyNameFromCertificate(List<AppNameInfo> appNameInfoList);
	List<AppNameInfo> getAppCompanyNameFromUser(List<AppNameInfo> appNameInfoList);
	List<AppNameInfo> handleAppNameInfo(List<AppNameInfo> appNameInfoList);
}
