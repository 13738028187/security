package com.hzdy.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzdy.service.ReportManagerService;

@Service("reportManagerService")
public class ReportManagerServiceImpl implements ReportManagerService {

	@Override
	public JSONObject getTableData(String startDate, String endDate) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {

			URL url = new URL("http://192.168.1.3:9200/syslog/_search");
			// http协议传输
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.setRequestProperty("Content-Type", "application/json");
			httpUrlConn.connect();
			
			OutputStreamWriter writer = new OutputStreamWriter(httpUrlConn.getOutputStream());  
            //发送参数
			if(startDate == null || "".equals(startDate) || endDate == null || "".equals(endDate)) {
				writer.write("{\r\n" + 
	            		"  \"size\": 0,\r\n" + 
	            		"  \"aggs\": {\r\n" + 
	            		"    \"deviceNames\": {\r\n" + 
	            		"      \"terms\": {\r\n" + 
	            		"        \"field\": \"logsource.keyword\",\r\n" + 
	            		"        \"order\": {\r\n" + 
	            		"          \"_count\": \"desc\"\r\n" + 
	            		"        }\r\n" + 
	            		"      },\r\n" + 
	            		"      \"aggs\": {\r\n" + 
	            		"        \"details\": {\r\n" + 
	            		"          \"top_hits\": {\r\n" + 
	            		"            \"sort\": [\r\n" + 
	            		"              {\r\n" + 
	            		"                \"@timestamp\": {\r\n" + 
	            		"                  \"order\": \"desc\"\r\n" + 
	            		"                }\r\n" + 
	            		"              }\r\n" + 
	            		"            ],\r\n" + 
	            		"            \"size\": 1\r\n" + 
	            		"          }\r\n" + 
	            		"        }\r\n" + 
	            		"      }\r\n" + 
	            		"    }\r\n" + 
	            		"  }\r\n" + 
	            		"}");  
			} else {
				writer.write("{\r\n" + 
						"	\"query\": {\r\n" + 
						"		\"bool\": {\r\n" + 
						"			\"must\": {\r\n" + 
						"				\"match_all\": {\r\n" + 
						"					\r\n" + 
						"				}\r\n" + 
						"			},\r\n" + 
						"			\"filter\": [{\r\n" + 
						"				\"range\": {\r\n" + 
						"					\"@timestamp\": {\r\n" + 
						"						\"gte\": \"" + startDate + "T00:00:00.000Z\",\r\n" + 
						"						\"lte\": \"" + endDate + "T00:00:00.000Z\"\r\n" + 
						"					}\r\n" + 
						"				}\r\n" + 
						"			}]\r\n" + 
						"		}\r\n" + 
						"	},\r\n" + 
						"	\"size\": 0,\r\n" + 
						"	\"aggs\": {\r\n" + 
						"		\"deviceNames\": {\r\n" + 
						"			\"terms\": {\r\n" + 
						"				\"field\": \"logsource.keyword\",\r\n" + 
						"				\"order\": {\r\n" + 
						"					\"_count\": \"desc\"\r\n" + 
						"				}\r\n" + 
						"			},\r\n" + 
						"			\"aggs\": {\r\n" + 
						"				\"details\": {\r\n" + 
						"					\"top_hits\": {\r\n" + 
						"						\"sort\": [{\r\n" + 
						"							\"@timestamp\": {\r\n" + 
						"								\"order\": \"desc\"\r\n" + 
						"							}\r\n" + 
						"						}],\r\n" + 
						"						\"size\": 1\r\n" + 
						"					}\r\n" + 
						"				}\r\n" + 
						"			}\r\n" + 
						"		}\r\n" + 
						"	}\r\n" + 
						"}");
			}
			/*System.out.println("{\r\n" + 
					"	\"query\": {\r\n" + 
					"		\"bool\": {\r\n" + 
					"			\"must\": {\r\n" + 
					"				\"match_all\": {\r\n" + 
					"					\r\n" + 
					"				}\r\n" + 
					"			},\r\n" + 
					"			\"filter\": [{\r\n" + 
					"				\"range\": {\r\n" + 
					"					\"@timestamp\": {\r\n" + 
					"						\"gte\": \"" + startDate + "T00:00:00.000Z\",\r\n" + 
					"						\"lte\": \"" + endDate + "T00:00:00.000Z\"\r\n" + 
					"					}\r\n" + 
					"				}\r\n" + 
					"			}]\r\n" + 
					"		}\r\n" + 
					"	},\r\n" + 
					"	\"size\": 0,\r\n" + 
					"	\"aggs\": {\r\n" + 
					"		\"deviceNames\": {\r\n" + 
					"			\"terms\": {\r\n" + 
					"				\"field\": \"logsource.keyword\",\r\n" + 
					"				\"order\": {\r\n" + 
					"					\"_count\": \"desc\"\r\n" + 
					"				}\r\n" + 
					"			},\r\n" + 
					"			\"aggs\": {\r\n" + 
					"				\"details\": {\r\n" + 
					"					\"top_hits\": {\r\n" + 
					"						\"sort\": [{\r\n" + 
					"							\"@timestamp\": {\r\n" + 
					"								\"order\": \"desc\"\r\n" + 
					"							}\r\n" + 
					"						}],\r\n" + 
					"						\"size\": 1\r\n" + 
					"					}\r\n" + 
					"				}\r\n" + 
					"			}\r\n" + 
					"		}\r\n" + 
					"	}\r\n" + 
					"}");*/
            
            //清理当前编辑器的左右缓冲区，并使缓冲区数据写入基础流  
            writer.flush();  
	        writer.close();  
			
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * 导出表格数据并转换为字节数组
	 * @return
	 */
	@Override
	public byte[] outPutTableData() {
		//创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		//新建sheet页
		HSSFSheet sheet = createHSSFSheetSetDefauleStyle(wb,"资产管理","A2:B2");
		//设置大标题
		createHeadlineAndSetDefaultStyle(wb,sheet,"资产管理统计表",4);
		//设置小标题/生成小标题
		String[] fieldsArray = {"序号","设备名称","设备IP","最新统计时间","告警数量"};
		createFieldsAndSetDefauleStyle(wb,sheet,fieldsArray,22);
		//设置内容
		String[] fieldsEnArray = {"id","deviceName","deviceIP","timestamp","alarmNum"};
		JSONObject obj= getTableData(null, null);
		JSONObject aggregations=(JSONObject) obj.get("aggregations");
		JSONObject deviceNames=(JSONObject) aggregations.get("deviceNames");
		JSONArray buckets=deviceNames.getJSONArray("buckets");
		List<Map<String, Object>>mlist=new ArrayList<>();
		for(int i =0;i<buckets.size();i++){
			JSONObject details=(JSONObject)buckets.getJSONObject(i).get("details");
			JSONObject hits=(JSONObject) details.get("hits");
			JSONObject h=(JSONObject)hits.getJSONArray("hits").get(0);
			JSONObject _source=(JSONObject)h.get("_source");
			
			Map<String, Object> map=new HashMap<>();
			map.put("id", i+1);
			map.put("deviceName", _source.getString("logsource"));
			map.put("deviceIP", _source.getString("host"));
			map.put("timestamp", _source.getString("timestamp"));
			map.put("alarmNum", hits.getString("total"));
			mlist.add(map);
		}
		System.out.println("-----"+mlist+"------");
		
		int total = buckets.size();
		JSONObject contentJson = new JSONObject();
		contentJson.put("fieldsEnArray", fieldsEnArray);
		contentJson.put("contents", mlist);
		contentJson.put("total", total);
		return setContentsAndDefaultStyle(wb,sheet,contentJson);
	}
	
	/**
	 * 创建sheet并设置默认的样式
	 * @param wb
	 * @param sheetName sheet名称
	 * @param autoFilter 要自动筛选的列 ps:"A1:B1"
	 * @return
	 */
	private HSSFSheet createHSSFSheetSetDefauleStyle(HSSFWorkbook wb,String sheetName,String autoFilter) {
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.createFreezePane( 0, 2, 0, 2 ); //冻结第二行
		CellRangeAddress c = CellRangeAddress.valueOf(autoFilter);//使A2到B2列添加筛选功能
		//sheet.setAutoFilter(c);//功能填入sheet中 自动筛选
		
		return sheet;
	}
	/**
	 * 创建大标题并设置默认样式
	 * @param sheet
	 * @param headlineCellStyle
	 * @param title 大标题
	 */
	private void createHeadlineAndSetDefaultStyle(HSSFWorkbook wb,HSSFSheet sheet,String title,int MergedCounts){
		//大标题样式
		CellStyle headlineCellStyle = setHeadlineStyle(wb);
		
		HSSFRow headlineRow = sheet.createRow(0);
		//创建要合并的单元格并设置样式
		for(int k = 1 ; k <= MergedCounts ;k++) {
			headlineRow.createCell(k).setCellStyle(headlineCellStyle);
		}
		HSSFCell headlineCell = headlineRow.createCell(0);
		headlineCell.setCellValue(title);
		headlineCell.setCellStyle(headlineCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,MergedCounts));//合并单元格
	}
	/**
	 * 设置大标题样式 2018-02-7
	 * @param cellStyle
	 * @param wb
	 * @return
	 */
	private CellStyle setHeadlineStyle (HSSFWorkbook wb)  {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);//下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框    
	    cellStyle.setBorderTop(BorderStyle.THIN);//上边框    
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框 
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
	    //设置字体样式
		HSSFFont hssfFont = wb.createFont();
		hssfFont.setFontHeightInPoints((short) 22);
		hssfFont.setFontName("宋体");
		hssfFont.setBold(true);
		cellStyle.setFont(hssfFont);
		return cellStyle;
	}
	/**
	 * 创建字段单元格，并设置默认样式
	 * @param wb
	 * @param sheet
	 * @param fieldsArray 字段名称
	 * @param rowHeight 行高
	 */
	private void createFieldsAndSetDefauleStyle(HSSFWorkbook wb,HSSFSheet sheet,String[] fieldsArray,float rowHeight){
		HSSFRow fieldsRow = sheet.createRow(1);
		fieldsRow.setHeightInPoints(rowHeight);//设置字段行高
		int fieldsArrayLength = fieldsArray.length;
		for(int i = 0;i < fieldsArrayLength ; i++) {
			HSSFCell fieldsCell = fieldsRow.createCell(i);
			fieldsCell.setCellValue(fieldsArray[i]);
			fieldsCell.setCellStyle(setFieldsCellStyle(wb));
			sheet.autoSizeColumn(i);//设置自适应列宽
		}
	}
	/**
	 * 设置字段样式 2018-02-7
	 * @param wb
	 * @return
	 */
	private CellStyle setFieldsCellStyle(HSSFWorkbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);//下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框    
	    cellStyle.setBorderTop(BorderStyle.THIN);//上边框    
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框 
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
		cellStyle.setLocked(true);
	    //设置字体样式
		HSSFFont hssfFont = wb.createFont();
		hssfFont.setFontHeightInPoints((short) 16);
		hssfFont.setFontName("宋体");
		hssfFont.setBold(true);
		
		cellStyle.setFont(hssfFont);
		return cellStyle;
	}
	/**
	 * 设置内容样式 2018-02-7
	 * @param wb
	 * @return
	 */
	private CellStyle setContentsCellStyle(HSSFWorkbook wb,short dataForma) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);//下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框    
	    cellStyle.setBorderTop(BorderStyle.THIN);//上边框    
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框 
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
		cellStyle.setDataFormat(dataForma);
	    //设置字体样式
		HSSFFont hssfFont = wb.createFont();
		hssfFont.setFontHeightInPoints((short) 12);
		hssfFont.setFontName("宋体");
		cellStyle.setFont(hssfFont);
		return cellStyle;
	}
	/**
	 * 填充内容并设置默认样式
	 * @param wb
	 * @param sheet
	 * @param contentJson
	 */
	@SuppressWarnings("unchecked")
	private byte[] setContentsAndDefaultStyle(HSSFWorkbook wb,HSSFSheet sheet,JSONObject contentJson) {
		
		JSONArray fieldsEnArray = contentJson.getJSONArray("fieldsEnArray");
		JSONArray contents = contentJson.getJSONArray("contents");
		double total = contentJson.getDoubleValue("total");
		

		int fieldsEnArrayLength = fieldsEnArray.size();
		int contentSize = contents.size();
		for(int i = 0 ; i < contentSize ; i++) {
			Map<String,Object> respectiveWorkArea = (Map<String, Object>) contents.get(i);
			HSSFRow contentRow = sheet.createRow(i+2);
		
			for(int j = 0;j < fieldsEnArrayLength ; j++) {
				HSSFCell contentCell = contentRow.createCell(j);
				String cellContent = new JSONObject(respectiveWorkArea).getString((String)fieldsEnArray.get(j));
				contentCell.setCellValue(cellContent);
				
			}
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			wb.write(outputStream);
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return outputStream.toByteArray();
	}
}
