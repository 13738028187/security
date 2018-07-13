package com.hzdy.service.impl;

import javax.annotation.Resource;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;
@Service("monitoringService")
public class MonitoringService {
	
	@Resource
	private CollectorService collector;
	
	private CollectorCenterService collectorServer;
	private int interval;
	
	public void collectorFixed() {
		collector.executeFixed();
		
	}

	public void collectorDynamic(String jobName, String jobGroup) throws SchedulerException {
		collector.execute(jobName,jobGroup);
	}

}
