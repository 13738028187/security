package com.hzdy.hardware.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.SchedulerRepository;

import com.hzdy.hardware.Parameters;
import com.hzdy.hardware.QuartzAdapter;

public class JobInformations implements Serializable {
	//是否支持Quartz
	static final boolean QUARTZ_AVAILABLE = isQuartzAvailable();
	private static final long serialVersionUID = -2826168112578815952L;
	private final String group;
	private final String name;
	private final String description;
	private final String jobClassName;
	private final Date previousFireTime;
	private final Date nextFireTime;
	private final long elapsedTime;
	private final long repeatInterval;
	private final String cronExpression;
	private final boolean paused;
	private final String globalJobId;

	JobInformations(JobDetail jobDetail, JobExecutionContext jobExecutionContext,
			Scheduler scheduler) throws SchedulerException {
	
		super();
		assert jobDetail != null;
		assert scheduler != null;
		
		final QuartzAdapter quartzAdapter = QuartzAdapter.getSingleton();
		this.group = quartzAdapter.getJobGroup(jobDetail);
		this.name = quartzAdapter.getJobName(jobDetail);
	
		this.description = quartzAdapter.getJobDescription(jobDetail);
		this.jobClassName = quartzAdapter.getJobClass(jobDetail).getName();
		if (jobExecutionContext == null) {
			elapsedTime = -1;
		} else {
			elapsedTime = System.currentTimeMillis()
					- quartzAdapter.getContextFireTime(jobExecutionContext).getTime();
		}
		//创建触发器集合
		final List<? extends Trigger>  triggers = quartzAdapter.getTriggersOfJob(jobDetail, scheduler);
		this.nextFireTime = getNextFireTime(triggers);
		this.previousFireTime = getPreviousFireTime(triggers);

		String cronTriggerExpression = null;
		long simpleTriggerRepeatInterval = -1;
		boolean jobPaused = true;
		for (final Trigger trigger : triggers) {
			//是否是定时触发器
			if (trigger instanceof CronTrigger) {
				cronTriggerExpression = quartzAdapter
						.getCronTriggerExpression((CronTrigger) trigger);
			} else if (trigger instanceof SimpleTrigger) {
				simpleTriggerRepeatInterval = quartzAdapter
						.getSimpleTriggerRepeatInterval((SimpleTrigger) trigger);
			}
			jobPaused = jobPaused && quartzAdapter.isTriggerPaused(trigger, scheduler);
		}
		this.repeatInterval = simpleTriggerRepeatInterval;
		this.cronExpression = cronTriggerExpression;
		this.paused = jobPaused;
		this.globalJobId = buildGlobalJobId(jobDetail);
	}
	//是否支持Quartz
	private static boolean isQuartzAvailable() {
		try {
			Class.forName("org.quartz.Job");
			Class.forName("org.quartz.impl.SchedulerRepository");
			return true;
		} catch (final ClassNotFoundException e) {
			return false;
		}
	}
	/**
	 * 静态方法
	 * 
	 * 构建JobInformation的集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static List<JobInformations> buildJobInformationsList() {
		//如果不支持Quartz
		if (!QUARTZ_AVAILABLE) {
			return Collections.emptyList();
		}
		//构建一个List<JobInformations>
		final List<JobInformations> result = new ArrayList<JobInformations>();
		try {
			//遍历所有的Scheduler,通过SchedulerRepository操作集合方法
			for (final Scheduler scheduler : getAllSchedulers()) {
				//JobExecutionContext用来添加额外的信息
				//创建一个currentlyExecutingJobsByFullName
				final Map<String, JobExecutionContext> currentlyExecutingJobsByFullName = new LinkedHashMap<String, JobExecutionContext>();
				//遍历scheduler里所有的JobExecutionContext
				for (final JobExecutionContext currentlyExecutingJob : (List<JobExecutionContext>) scheduler.getCurrentlyExecutingJobs()) {
					//JobDetail就是用来将Job封装的
					//获取JobDetail 通过Job的JobExecutionContext.getJobDetail()
					final JobDetail jobDetail = QuartzAdapter.getSingleton().getContextJobDetail(currentlyExecutingJob);
					//获取JobFullName的全名
					final String jobFullName = QuartzAdapter.getSingleton().getJobFullName(jobDetail);
					currentlyExecutingJobsByFullName.put(jobFullName, currentlyExecutingJob);
				}
				for (final JobDetail jobDetail : getAllJobsOfScheduler(scheduler)) {
					final String jobFullName = QuartzAdapter.getSingleton()
							.getJobFullName(jobDetail);
					final JobExecutionContext jobExecutionContext = currentlyExecutingJobsByFullName
							.get(jobFullName);
					result.add(new JobInformations(jobDetail, jobExecutionContext, scheduler));
				}
			}
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
		return result;
	}
	//获取所有的调度器
	@SuppressWarnings("unchecked")
	public static List<Scheduler> getAllSchedulers() {
		//SchedulerRepository非常简单,相当于对Scheduler的集合控制
		//lookupAll:获取所有的Scheduler
		return new ArrayList<Scheduler>(SchedulerRepository.getInstance().lookupAll());
	}

	static List<JobDetail> getAllJobsOfScheduler(Scheduler scheduler) {
		try {
			return QuartzAdapter.getSingleton().getAllJobsOfScheduler(scheduler);
		} catch (final Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
    //获取上次启动的时间
	private static Date getPreviousFireTime(List<? extends Trigger> triggers) {
		Date previousFireTime = null;
		for (final Trigger trigger : triggers) {
			final Date triggerPreviousFireTime = QuartzAdapter.getSingleton()
					.getTriggerPreviousFireTime(trigger);
			if (previousFireTime == null || triggerPreviousFireTime != null
					&& previousFireTime.before(triggerPreviousFireTime)) {
				previousFireTime = triggerPreviousFireTime;
			}
		}
		return previousFireTime;
	}
    //获取下次执行的时间
	private static Date getNextFireTime(List<? extends Trigger> triggers) {
		Date nextFireTime = null;
		for (final Trigger trigger : triggers) {
			final Date triggerNextFireTime = QuartzAdapter.getSingleton()
					.getTriggerNextFireTime(trigger);
			if (nextFireTime == null
					|| triggerNextFireTime != null && nextFireTime.after(triggerNextFireTime)) {
				nextFireTime = triggerNextFireTime;
			}
		}
		return nextFireTime;
	}

	String getGlobalJobId() {
		return globalJobId;
	}

	String getName() {
		return name;
	}

	String getGroup() {
		return group;
	}

	String getDescription() {
		return description;
	}

	String getJobClassName() {
		return jobClassName;
	}

	long getElapsedTime() {
		return elapsedTime;
	}

	boolean isCurrentlyExecuting() {
		return elapsedTime >= 0;
	}

	Date getNextFireTime() {
		return nextFireTime;
	}

	Date getPreviousFireTime() {
		return previousFireTime;
	}

	long getRepeatInterval() {
		return repeatInterval;
	}

	String getCronExpression() {
		return cronExpression;
	}

	boolean isPaused() {
		return paused;
	}
    //构建一个全局JobId
	private static String buildGlobalJobId(JobDetail jobDetail) {
		return PID.getPID() + '_' + Parameters.getHostAddress() + '_'
				+ QuartzAdapter.getSingleton().getJobFullName(jobDetail).hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[name=" + getName() + ", group=" + getGroup() + ']';
	}
}
