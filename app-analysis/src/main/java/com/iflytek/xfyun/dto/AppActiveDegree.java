package com.iflytek.xfyun.dto;


public class AppActiveDegree implements Comparable<AppActiveDegree>{
	private String appId;//应用ID
	private String appName;//应用名称
	private String platform;//平台
	private int activeMonthly;//月活
	private long serviceCountMonthly;//月服务量
	private double newRegUsersRateMonthly;//月新增注册用户增长率
	private double usersRateMonthly;//月新增活跃用户增长率
	private double retentRateMonthly;//月活跃用户留存率
	private double activeMonthlyScore;//月活得分
	private double serviceCountScore;//服务量得分
	private double incRegUsersScore;//新增注册用户得分
	private double incActUsersScore;//新增活跃用户得分
	private double retentUsersScore;//活跃用户留存得分
	private String company;//app所属公司
	private double score;//总得分
	private int  timestamp;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	public double getActiveMonthlyScore() {
		return activeMonthlyScore;
	}
	public void setActiveMonthlyScore(double activeMonthlyScore) {
		this.activeMonthlyScore = activeMonthlyScore;
	}
	public double getServiceCountScore() {
		return serviceCountScore;
	}
	public void setServiceCountScore(double serviceCountScore) {
		this.serviceCountScore = serviceCountScore;
	}
	public double getIncRegUsersScore() {
		return incRegUsersScore;
	}
	public void setIncRegUsersScore(double incRegUsersScore) {
		this.incRegUsersScore = incRegUsersScore;
	}
	public double getIncActUsersScore() {
		return incActUsersScore;
	}
	public void setIncActUsersScore(double incActUsersScore) {
		this.incActUsersScore = incActUsersScore;
	}
	public double getRetentUsersScore() {
		return retentUsersScore;
	}
	public void setRetentUsersScore(double retentUsersScore) {
		this.retentUsersScore = retentUsersScore;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public double getUsersRateMonthly() {
		return usersRateMonthly;
	}
	public void setUsersRateMonthly(double usersRateMonthly) {
		this.usersRateMonthly = usersRateMonthly;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getActiveMonthly() {
		return activeMonthly;
	}
	public void setActiveMonthly(int activeMonthly) {
		this.activeMonthly = activeMonthly;
	}
	public long getServiceCountMonthly() {
		return serviceCountMonthly;
	}
	public void setServiceCountMonthly(long serviceCountMonthly) {
		this.serviceCountMonthly = serviceCountMonthly;
	}
	public double getNewRegUsersRateMonthly() {
		return newRegUsersRateMonthly;
	}
	public void setNewRegUsersRateMonthly(double newRegUsersRateMonthly) {
		this.newRegUsersRateMonthly = newRegUsersRateMonthly;
	}
	public double getRetentRateMonthly() {
		return retentRateMonthly;
	}
	public void setRetentRateMonthly(double retentRateMonthly) {
		this.retentRateMonthly = retentRateMonthly;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	//根据得分降序排列
	@Override
	public int compareTo(AppActiveDegree otherApp) {
		double otherScore = otherApp.getScore();
		return new Double(otherScore).compareTo(this.score);
//		int otherUsers = otherApp.getActiveMonthly();
//		return new Integer(otherUsers).compareTo(this.activeMonthly);
	}
}
