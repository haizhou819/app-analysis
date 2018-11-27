package com.iflytek.xfyun.dao.impl;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import com.iflytek.xfyun.dao.AppActiveDao;
import com.iflytek.xfyun.dto.AppActiveDegree;
@Repository
public class AppActiveDaoImol extends SqlSessionDaoSupport implements AppActiveDao{
	@Override
	public AppActiveDegree getAppActiveInfoByNameOrId(Map<String, Object> params) {
		AppActiveDegree appActiveDegree = this.getSqlSession().selectOne("AppActiveDegreeMapper.getAppActiveInfoByNameOrId", params);
		return appActiveDegree;
	}

	@Override
	public List<AppActiveDegree> getAppActiveInfosByRank(Map<String, Object> params) {
		List<AppActiveDegree> appActiveDegreeList = this.getSqlSession().selectList("AppActiveDegreeMapper.getAppActiveInfosByRank", params);
		return appActiveDegreeList;
	}
	
	@Override
	public List<AppActiveDegree> getAllAppActiveInfos(Map<String, Object> params) {
		List<AppActiveDegree> appActiveDegreeList = this.getSqlSession().selectList("AppActiveDegreeMapper.getAllAppActiveInfos", params);
		return appActiveDegreeList;
	}

	@Override
	public void saveAppActiveInfos(List<AppActiveDegree> appActiveDegreeList) {
		this.getSqlSession().insert("AppActiveDegreeMapper.saveAppActiveInfos", appActiveDegreeList);
	}
	
	/*public static void main(String[] args) {
		ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext.xml");
		DataSourceContextHolder.setDbType(DataSources.DS1);
		AppActiveDaoImol appActiveDaoImol = (AppActiveDaoImol)ctx.getBean("appActiveDaoImol");
		///////////////////////////////////测试获取多条数据//////////////////////////////////////////////////////
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("timestamp", "2016-06-01");
		params.put("rank", 20);
		
		List<AppActiveDegree> appActiveDegreeList = appActiveDaoImol.getAppActiveInfosByRank(params);
		//List<AppActiveDegree> appActiveDegreeList = appActiveDaoImol.getAllAppActiveInfos(params);
		//System.out.println(appActiveDegreeList.get(7).getAppName());
		System.out.println(appActiveDegreeList.size());
		ExcelDisplayServiceImpl excelDisplayServiceImpl = (ExcelDisplayServiceImpl)ctx.getBean("excelDisplayServiceImpl");
		excelDisplayServiceImpl.displayAppDataByExcel(appActiveDegreeList);
		
		////////////////////////////////////测试根据name或id获取数据////////////////////////////////////////////////////////
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("timestamp", "2016-06-01");
		params.put("appId", "5088fd2e");
		AppActiveDegree appActiveDegree = appActiveDaoImol.getAppActiveInfoByNameOrId(params);
		System.out.println(appActiveDegree.getAppName());
	}*/
}
