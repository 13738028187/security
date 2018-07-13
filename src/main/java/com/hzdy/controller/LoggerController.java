package com.hzdy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzdy.logger.entity.Syslog;
import com.hzdy.logger.entity.SystemLogger;
import com.hzdy.logger.entity.WebLogger;
import com.hzdy.service.SyslogService;
import com.hzdy.service.SystemLoggerService;
import com.hzdy.service.WebLoggerService;
import com.hzdy.service.impl.EmailServiceImpl;
import com.hzdy.utils.R;

@Controller
@RequestMapping(value = "/logger")
public class LoggerController {
	
	@Resource
	private SyslogService syslogService;
	@Resource
	private EmailServiceImpl emailService;
	@Resource
	private SystemLoggerService systemLoggerService;
	@Resource
	private WebLoggerService webLoggerService;

	@MessageMapping("/topic")
	@SendTo("/topic/addLoanPoint")
	@ResponseBody
    public String greeting(String message) throws Exception {

		System.out.println("-----------------success----------------");
		return message;
	}

//	@MessageMapping("/warn")
//	@SendTo("/topic/getWarnMessage")
//	@ResponseBody
//	public Message getWarnMessage(Message message) throws Exception {
//
//		System.out.println("-----------------warn----------------");
//		return message;
//	}

	// 日志检索服务Method
	@RequestMapping(value = "/systemLoggerlist", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject systemLoggerlist(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "rows", defaultValue = "10") String rows) {
		List<SystemLogger> lists = systemLoggerService.queryAll();//返回類型
		int totalRecord = lists.size();
		int totalPage = (totalRecord - 1 + Integer.parseInt(rows)) / Integer.parseInt(rows);
		int index = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("total", totalPage);
        jsonObj.put("page", page);
        jsonObj.put("records", totalRecord);
        //日誌類型：日誌，等級，類和段落，消息
        //日誌類型先用mysql後期用Mongodb
        JSONArray row = new JSONArray();
        for (int i = index; i < Integer.parseInt(rows) + index && i < totalRecord; i++) {
        	JSONObject cell = new JSONObject();
        	cell.put("systemLoggerId",lists.get(i).getSystemLoggerId());
        	cell.put("logName",lists.get(i).getLogName());
        	cell.put("logType",lists.get(i).getLogType());
        	cell.put("logContent",lists.get(i).getLogContent());
        	cell.put("logMessage",lists.get(i).getLogMessage());

        	//字符      	        	 	
            row.add(cell);
        }
        jsonObj.put("rows", row);
        
        return jsonObj;
	}

	@RequestMapping(value = "/webLoggerlist", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject webLoggerlist(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "rows", defaultValue = "10") String rows) {
		
		List<WebLogger> lists = webLoggerService.queryAll();
		int totalRecord = lists.size();
		int totalPage = (totalRecord - 1 + Integer.parseInt(rows)) / Integer.parseInt(rows);
		int index = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("total", totalPage);
        jsonObj.put("page", page);
        jsonObj.put("records", totalRecord);
        //字段設計:ip地址，日誌，網址，HTTP類型,返回結果，瀏覽器類型，瀏覽器類型和HTTP類型放入纖細信息
        JSONArray row = new JSONArray();
        for (int i = index; i < Integer.parseInt(rows) + index && i < totalRecord; i++) {
        	JSONObject cell = new JSONObject();
        	cell.put("webLoggerId",lists.get(i).getWebLoggerId()); 
        	cell.put("ipAddress",lists.get(i).getIpAddress());  
        	cell.put("url",lists.get(i).getUrl()); 
        	cell.put("HTTPType",lists.get(i).getHTTPType());    
        	cell.put("returnType",lists.get(i).getReturnType()); 
        	cell.put("browserType",lists.get(i).getBrowserType());    
            row.add(cell);
        }
        jsonObj.put("rows", row);
        
        return jsonObj;
	}
	
	@RequestMapping(value = "/syslog", method = RequestMethod.GET)
	@ResponseBody
	public R syslog(String ip) {
		Map<String,Object> params=new HashMap<>();
		params.put("host", ip);
		List<Syslog> list=syslogService.findAll(params, "log");
		return R.ok().put("list", list);
	}
}
