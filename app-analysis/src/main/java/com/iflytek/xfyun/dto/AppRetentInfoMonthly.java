package com.iflytek.xfyun.dto;

import java.security.Timestamp;

public class AppRetentInfoMonthly {
	private String appId;//应用ID
	private int retent1;//上个月活跃用户在当月的留存数量
	private int lastActiveUsers;//上个月的活跃用户数
	private Timestamp  startTime;//计算存量数据的截止日期，默认是每个月的月底
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public int getRetent1() {
		return retent1;
	}
	public void setRetent1(int retent1) {
		this.retent1 = retent1;
	}
	public int getLastActiveUsers() {
		return lastActiveUsers;
	}
	public void setLastActiveUsers(int lastActiveUsers) {
		this.lastActiveUsers = lastActiveUsers;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
}
