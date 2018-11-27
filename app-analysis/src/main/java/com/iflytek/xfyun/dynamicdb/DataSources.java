package com.iflytek.xfyun.dynamicdb;


public enum DataSources {
	//,DS4("appPackDataSource"),DS5("appNameDataSource")
	DS1("nativeDataSource"),DS2("appBizDataSource"),DS3("appRetentDataSource"),DS4("appCompanyDataSource"),DS6("appNameDataSource");
	
	private DataSources(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private String name;
}
