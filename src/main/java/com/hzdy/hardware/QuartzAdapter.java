package com.hzdy.hardware;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.hzdy.hardware.entity.JobInformations;
//为了方便控制调度器
public class QuartzAdapter {
	private static final boolean QUARTZ_2 = isQuartz2();
	// 创建一个单例
	private static final QuartzAdapter SINGLETON = createSingleton();

	protected QuartzAdapter() {
		super();
	}

	public static QuartzAdapter getSingleton() {
		return SINGLETON;
	}

	// 是否是isQuartz2
	private static boolean isQuartz2() {
		try {
			Class.forName("org.quartz.JobKey");
			return true;
		} catch (final ClassNotFoundException e) {
			return false;
		}
	}

	// 创建单例
	private static QuartzAdapter createSingleton() {
		// 判断QUARTZ_2值进行判断
		if (QUARTZ_2) {
			try {
				return (QuartzAdapter) Class.forName("net.bull.javamelody.Quartz2Adapter").newInstance();
			} catch (final Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return new QuartzAdapter();
	}
	//获取Job名字
	public String getJobName(JobDetail jobDetail) {
		return jobDetail.getKey().getName();
		/* return jobDetail.getName(); */
	}
	//获取Job组
	public String getJobGroup(JobDetail jobDetail) {
		return jobDetail.getKey().getGroup();
		/* return jobDetail.getGroup(); */
	}
	//获取Job全名
	public String getJobFullName(JobDetail jobDetail) {
		return getJobGroup(jobDetail) + '.' + getJobName(jobDetail);
	}
	//获得Job详细信息
	public String getJobDescription(JobDetail jobDetail) {
		return jobDetail.getDescription();
	}
	//获取Job类
	public Class<?> getJobClass(JobDetail jobDetail) {
		return jobDetail.getJobClass();
	}
    //获取上次启动的触发器
	public Date getTriggerPreviousFireTime(Trigger trigger) {
		return trigger.getPreviousFireTime();
	}
    //获取下次启动的触发器
	public Date getTriggerNextFireTime(Trigger trigger) {
		return trigger.getNextFireTime();
	}
    //获取触发器表达式
	public String getCronTriggerExpression(CronTrigger trigger) {
		// getCronExpression gives a PMD false+
		return trigger.getCronExpression();
	}
    //获得触发器的间隔
	public long getSimpleTriggerRepeatInterval(SimpleTrigger trigger) {
		return trigger.getRepeatInterval();
	}
    //根据JobExecutionContext获取JobDetail
	public JobDetail getContextJobDetail(JobExecutionContext context) {
		return context.getJobDetail();
	}
	//根据JobExecutionContext获取启动时间
	public Date getContextFireTime(JobExecutionContext context) {
		return context.getFireTime();
	}
	/**
	 * 添加添加触发器的全局监听器
	 * @param jobGlobalListener
	 * @throws SchedulerException
	 */
	public void addGlobalJobListener(JobListener jobGlobalListener) throws SchedulerException {
		//创建一个默认的调度器
		final Scheduler defaultScheduler;
		//设置名字和组
		/*KeyMatcher<JobKey> keyMatcher;*/
		if (Boolean.parseBoolean(Parameters.getParameter(Parameter.QUARTZ_DEFAULT_LISTENER_DISABLED))) {
			defaultScheduler = null;
		/*	LOG.debug("Initialization of Quartz default listener has been disabled");*/
		} else {
			//用来创建调度器
			defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
			//给调度器添加一个全局监听器
			/*defaultScheduler.getListenerManager().addGlobalJobListener(jobGlobalListener);*/
			defaultScheduler.getListenerManager().addJobListener(jobGlobalListener);
		}
		for (final Scheduler scheduler : JobInformations.getAllSchedulers()) {
			if (scheduler != defaultScheduler) {
			/*	scheduler.addGlobalJobListener(jobGlobalListener);*/
				//给调度器添加一个全局监听器
				scheduler.getListenerManager().addJobListener(jobGlobalListener);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void removeGlobalJobListener() throws SchedulerException {
		for (final Scheduler scheduler : JobInformations.getAllSchedulers()) {
			/*final List<JobListener> globalJobListeners = scheduler.getGlobalJobListeners();*/
			final List<JobListener> globalJobListeners = scheduler.getListenerManager().getJobListeners();
			for (final JobListener jobListener : new ArrayList<JobListener>(globalJobListeners)) {
				if (jobListener instanceof JobGlobalListener) {
					try {
					/*	scheduler.removeGlobalJobListener(jobListener);*/
						scheduler.getListenerManager().removeJobListener(jobListener.getName());
					} catch (final NoSuchMethodError e1) {
						// pour Quartz 1.7, 1.8 et +,
						// cette méthode n'existe pas avant Quartz 1.6
						try {
							final Class<? extends Scheduler> schedulerClass = scheduler.getClass();
							schedulerClass.getMethod("removeGlobalJobListener", String.class).invoke(scheduler,
									jobListener.getName());
						} catch (final Exception e2) {
							throw new IllegalArgumentException(e2);
						}
					}
				}
			}
		}
	}

	public List<JobDetail> getAllJobsOfScheduler(Scheduler scheduler) throws SchedulerException {
		final List<JobDetail> result = new ArrayList<JobDetail>();
		for (final String jobGroupName : scheduler.getJobGroupNames()) {
			/*
			 * for (final String jobName : scheduler.getJobNames(jobGroupName))
			 * {
			 */
				for (final String jobName : scheduler.getJobGroupNames()) {
				final JobDetail jobDetail;
				try {
					JobKey jobKey=new JobKey(jobName,jobGroupName);
				/*	jobDetail = scheduler.getJobDetail(jobName, jobGroupName);*/
					jobDetail = scheduler.getJobDetail(jobKey);
					if (jobDetail != null) {
						result.add(jobDetail);
					}
				} catch (final Exception e) {
					/*LOG.debug(e.toString(), e);*/
				}
			}
		}
		return result;
	}
    //List<Trigger>
	public List<? extends Trigger> getTriggersOfJob(JobDetail jobDetail, Scheduler scheduler) throws SchedulerException {
	/*	return Arrays.asList(scheduler.getTriggersOfJob(jobDetail.getName(), jobDetail.getGroup()));*/
		return scheduler.getTriggersOfJob(jobDetail.getKey());
	}

	public boolean isTriggerPaused(Trigger trigger, Scheduler scheduler) throws SchedulerException {
/*		return scheduler.getTriggerState(trigger.getName(), trigger.getGroup()) == Trigger.STATE_PAUSED;*/
		return scheduler.getTriggerState(trigger.getKey()) == Trigger.TriggerState.PAUSED;
	}

	public void pauseJob(JobDetail jobDetail, Scheduler scheduler) throws SchedulerException {
/*		scheduler.pauseJob(jobDetail.getName(), jobDetail.getGroup());*/
		scheduler.pauseJob(jobDetail.getKey());
	}

	public void resumeJob(JobDetail jobDetail, Scheduler scheduler) throws SchedulerException {
		/*scheduler.resumeJob(jobDetail.getName(), jobDetail.getGroup());*/
		scheduler.resumeJob(jobDetail.getKey());
	}
}
