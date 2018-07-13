package com.hzdy.hardware;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hzdy.service.impl.MonitoringService;

public class MonitoringListener implements ServletContextListener {
	private static boolean instanceCreated;
	private boolean instanceEnabled;
	@SuppressWarnings("all")
	private static final List<String> CONTEXT_PATHS = new ArrayList<String>();

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);

		MonitoringService monitoringService = (MonitoringService) ctx.getBean("monitoringService");
		// 获取项目根路径
		final String contextPath = Parameters.getContextPath(event.getServletContext());
		if (!instanceEnabled) {
			if (!CONTEXT_PATHS.contains(contextPath)) {
				instanceEnabled = true;
			} else {
				return;
			}
		}
		CONTEXT_PATHS.add(contextPath);
		Parameters.initialize(event.getServletContext());

		// 执行监控,数据开始收集
		doMonitoring(monitoringService);

	}

	private void doMonitoring(MonitoringService monitoringService) {
		// 首先收集固定数据
		monitoringService.collectorFixed();
		// 开始收集动态数据
		try {
			monitoringService.collectorDynamic("collector", "collector service");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
