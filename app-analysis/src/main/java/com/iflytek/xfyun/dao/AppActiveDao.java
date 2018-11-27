package com.iflytek.xfyun.dao;

import java.util.List;
import java.util.Map;

import com.iflytek.xfyun.dto.AppActiveDegree;

public interface AppActiveDao {
	AppActiveDegree getAppActiveInfoByNameOrId(Map<String,Object> params);
	List<AppActiveDegree> getAppActiveInfosByRank(Map<String,Object> params);
	List<AppActiveDegree> getAllAppActiveInfos(Map<String,Object> params);
	void saveAppActiveInfos(List<AppActiveDegree> appActiveDegreeList);
}
