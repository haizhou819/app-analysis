package com.iflytek.xfyun.service.rule;

import java.util.List;

import com.iflytek.xfyun.dto.AppActiveDegree;

public interface ICalculateRuleService {
	List<AppActiveDegree> getAppSore(List<AppActiveDegree> appActiveDegreeList);
}
