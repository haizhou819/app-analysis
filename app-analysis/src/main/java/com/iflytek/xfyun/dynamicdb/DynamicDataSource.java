package com.iflytek.xfyun.dynamicdb;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource implements ApplicationContextAware {

	@Override
	public Logger getParentLogger() {
		return null;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		System.out.println(DataSourceContextHolder.getDbType());
		return DataSourceContextHolder.getDbType();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		DataSources[] dss = DataSources.values();
		setDefaultTargetDataSource(applicationContext.getBean(dss[0].getName()));
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		for (DataSources dataSources : dss) {
			targetDataSources.put(dataSources, applicationContext.getBean(dataSources.getName()));
		}
		setTargetDataSources(targetDataSources);
	}
}