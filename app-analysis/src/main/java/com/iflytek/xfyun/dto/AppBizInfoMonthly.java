package com.iflytek.xfyun.dto;

public class AppBizInfoMonthly {
	private String appId;//应用ID
	private int currentNewRegUsers;//当月新增注册用户数
	private int lastNewRegUsers;//上月新增注册用户数
	private int currentUsers;//当月用户数
	private int lastUsers;//上月用户数
	private long serviceCount;//当月服务量
	private int timestamp;
	
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	public long getServiceCount() {
		return serviceCount;
	}
	public void setServiceCount(long serviceCount) {
		this.serviceCount = serviceCount;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public int getCurrentNewRegUsers() {
		return currentNewRegUsers;
	}
	public void setCurrentNewRegUsers(int currentNewRegUsers) {
		this.currentNewRegUsers = currentNewRegUsers;
	}
	public int getLastNewRegUsers() {
		return lastNewRegUsers;
	}
	public void setLastNewRegUsers(int lastNewRegUsers) {
		this.lastNewRegUsers = lastNewRegUsers;
	}
	public int getCurrentUsers() {
		return currentUsers;
	}
	public void setCurrentUsers(int currentUsers) {
		this.currentUsers = currentUsers;
	}
	public int getLastUsers() {
		return lastUsers;
	}
	public void setLastUsers(int lastUsers) {
		this.lastUsers = lastUsers;
	}
}
