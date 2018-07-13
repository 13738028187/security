package com.hzdy.hardware;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public final class UpdateChecker {
	private Trigger trigger;

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	public UpdateChecker() {
		super();
		this.trigger = null;
	}

	public Trigger setJobNameAndGroup(String jobName, String jobGroup) {
		return trigger = TriggerBuilder.newTrigger().withIdentity(jobName,jobGroup)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
	}

}
