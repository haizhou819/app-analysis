package com.iflytek.xfyun.dynamicdb;


public class DataSourceContextHolder {
	private static final ThreadLocal<DataSources> contextHolder = new ThreadLocal<DataSources>();

	public static void setDbType(DataSources dbType) {
		clearDbType();
		contextHolder.set(dbType);
	}

	public static DataSources getDbType() {
		return ((DataSources) contextHolder.get());
	}

	public static void clearDbType() {
		contextHolder.remove();
	}
}
