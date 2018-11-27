package com.iflytek.xfyun.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigUtil {
	private static Properties props = new Properties();
	static {
		try {
			props.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"), "UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getInt(String key,int defaultValue){
		String value = props.getProperty(key);
		if("".equals(value)||value == null){
			return defaultValue;
		}
		try{
			
			return Integer.parseInt(value);
			
		}catch(Exception ex){
			return defaultValue;
		}
	}

	public static String getValue(String key) {
		return props.getProperty(key);
	}

	public static String getValue(String key,String defValue) {
		String ret= props.getProperty(key);
		if(ret==null||ret.isEmpty()||ret.equals("")){
			return defValue;
		}
		return ret;
	}
	
	public static void updateProperties(String key, String value) {
		props.setProperty(key, value);
	}
	
}
