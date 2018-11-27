package com.iflytek.xfyun.dao;

import java.util.List;
import java.util.Map;

import com.iflytek.xfyun.dto.AppActiveDegree;
import com.iflytek.xfyun.dto.AppBizInfoMonthly;

public interface AppBizDao {
	void saveAppActiveData(AppActiveDegree appActiveDegree);
	void updateAppActiveData(AppActiveDegree appActiveDegree);
	List<AppActiveDegree> getAppActiveInfo(Map<String,Object> params);
	AppBizInfoMonthly getAppBizInfoMonthly(Map<String,Object> params);
	List<AppBizInfoMonthly> getAppBizInfoMonthlyList(Map<String,Object> params);
}
