package com.hzdy.service.impl;

import javax.annotation.Resource;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import com.hzdy.hardware.CollecotrJob;
import com.hzdy.hardware.UpdateChecker;
import com.hzdy.hardware.entity.SystemInfo;
import com.hzdy.hardware.entity.SystemInfoFactory;


@Service("collectorService")
public class CollectorService {
	
	@Resource
	private SystemInfoServiceImpl systemInfoService;
	private JobDetail job;
	private static CollectorService collectorService;
	private Scheduler scheduler;

	public static CollectorService getInstance() {
		if (collectorService == null) {
			collectorService = new CollectorService();
			return collectorService;
		}
		return collectorService;
	}
	public void execute(String jobName, String jobGroup) throws SchedulerException {
		JobDetail job = JobBuilder.newJob(CollecotrJob.class).withIdentity(jobName, jobGroup).build();
		UpdateChecker updateChecker = new UpdateChecker();
		Trigger trigger = updateChecker.setJobNameAndGroup(jobName, jobGroup);
		scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
		
	}

	public void executeFixed() {
		SystemInfo systemInfo = new SystemInfo();
		SystemInfoFactory.getInstance().build(systemInfo);
		systemInfoService.save(systemInfo);
	}
}
