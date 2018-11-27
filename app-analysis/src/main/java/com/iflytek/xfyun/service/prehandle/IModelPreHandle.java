package com.iflytek.xfyun.service.prehandle;

import java.util.List;
import java.util.Map;

import com.iflytek.xfyun.dto.AppActiveDegree;

public interface IModelPreHandle {
	List<AppActiveDegree> mergeAndConvert(Map<String,Object> params,List<String> appIdList);
	List<AppActiveDegree> mergeAndConvert(Map<String,Object> params);
}
