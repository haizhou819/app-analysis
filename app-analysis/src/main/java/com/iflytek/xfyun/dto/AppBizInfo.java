package com.iflytek.xfyun.dto;

public class AppBizInfo {
	private String appId;
	private AppBizInfoMonthly appBizInfoMonthly;
	private AppRetentInfoMonthly appRetentInfoMonthly;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public AppBizInfoMonthly getAppBizInfoMonthly() {
		return appBizInfoMonthly;
	}
	public void setAppBizInfoMonthly(AppBizInfoMonthly appBizInfoMonthly) {
		this.appBizInfoMonthly = appBizInfoMonthly;
	}
	public AppRetentInfoMonthly getAppRetentInfoMonthly() {
		return appRetentInfoMonthly;
	}
	public void setAppRetentInfoMonthly(AppRetentInfoMonthly appRetentInfoMonthly) {
		this.appRetentInfoMonthly = appRetentInfoMonthly;
	}
}
