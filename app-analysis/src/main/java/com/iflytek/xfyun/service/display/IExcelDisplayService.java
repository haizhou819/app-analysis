package com.iflytek.xfyun.service.display;

import java.util.List;

import com.iflytek.xfyun.dto.AppActiveDegree;


public interface IExcelDisplayService {
	void displayAppDataByExcel(List<AppActiveDegree> appActiveDegreeList);
}
