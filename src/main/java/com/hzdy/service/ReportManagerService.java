package com.hzdy.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 报表管理业务类
 * @author yuwenming
 *
 */
@Service("reportManagerService")
public interface ReportManagerService {
	
	/**
	 * 获取表格需要的数据
	 * @return
	 */
	JSONObject  getTableData(String startDate, String endDate);
	
	byte[] outPutTableData();

}
