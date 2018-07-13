package com.hzdy.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.hzdy.service.ReportManagerService;
import com.hzdy.utils.R;

@Controller
@RequestMapping("reportManager")
public class ReportManagerController {

	@Resource
	private ReportManagerService reportManagerService;

	@ResponseBody
	@RequestMapping(value = "getTableData")
	public JSONObject getTableData(@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "rows", defaultValue = "10") String rows,
			@RequestParam(value = "startDate", defaultValue = "") String startDate,
			@RequestParam(value = "endDate", defaultValue = "") String endDate) {
		JSONObject tableJson = reportManagerService.getTableData(startDate, endDate);
		System.out.println(tableJson);
		JSONArray tableData = tableJson.getJSONObject("aggregations").getJSONObject("deviceNames")
				.getJSONArray("buckets");
		int totalRecord = tableData.size();

		int totalPage = (totalRecord - 1 + Integer.parseInt(rows)) / Integer.parseInt(rows);
		int index = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("total", totalPage);
		jsonObj.put("page", page);
		jsonObj.put("records", totalRecord);

		JSONArray row = new JSONArray();
		for (int i = index; i < Integer.parseInt(rows) + index && i < totalRecord; i++) {
			JSONObject cell = new JSONObject();
			cell.put("id", i + 1);
			cell.put("deviceName", tableData.getJSONObject(i).getString("key"));
			cell.put("deviceIP", tableData.getJSONObject(i).getJSONObject("details").getJSONObject("hits")
					.getJSONArray("hits").getJSONObject(0).getJSONObject("_source").getString("host"));
			cell.put("timestamp", tableData.getJSONObject(i).getJSONObject("details").getJSONObject("hits")
					.getJSONArray("hits").getJSONObject(0).getJSONObject("_source").getString("timestamp"));
			cell.put("alarmNum", tableData.getJSONObject(i).getString("doc_count"));

			row.add(cell);
		}

		jsonObj.put("rows", row);

		return jsonObj;
	}	
	/**
	 * 导出柱状折线图的excel文件
	 * @param response
	 */
	@RequestMapping("exported")
	public void exported(HttpServletResponse response) {
		try {
			//获取表格数据字节数组
			byte[] data = reportManagerService.outPutTableData();
			//给响应设置属性
			String filename = "设备信息";
			filename = new String(filename.getBytes(), "ISO-8859-1");
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");
			response.setContentType("application/msexcel;charset=UTF-8");
			response.addHeader("Content-Length", "" + data.length);
			//给前端导出excel
			IOUtils.write(data, response.getOutputStream());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
