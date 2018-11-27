package com.iflytek.xfyun.dao;

import java.util.List;
import java.util.Map;

import com.iflytek.xfyun.dto.AppRetentInfoMonthly;

public interface AppRetentDao {
	AppRetentInfoMonthly getAppRetentInfoMonthly(Map<String,Object> params);
	List<AppRetentInfoMonthly> getAppRetentInfoMonthlyByIds(Map<String,Object> param);
}
