package com.hzdy.hardware;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;


final class JobGlobalListener implements JobListener {
/*	private static final Counter JOB_COUNTER = new Counter(Counter.JOB_COUNTER_NAME, "jobs.png",
			JdbcWrapper.SINGLETON.getSqlCounter());

	static Counter getJobCounter() {
		return JOB_COUNTER;
	}*/
    //初始化事件
	static void initJobGlobalListener() {
		try {
			//新建一个监听器
			final JobGlobalListener jobGlobalListener = new JobGlobalListener();
			//添加监听器
			QuartzAdapter.getSingleton().addGlobalJobListener(jobGlobalListener);
		/*	LOG.debug("job global listener initialized");*/
		} catch (final SchedulerException e) {
		/*	LOG.info("initialization of job global listener failed, skipping", e);*/
		}
	}
    //销毁监听器
	static void destroyJobGlobalListener() {
		try {
			QuartzAdapter.getSingleton().removeGlobalJobListener();
		} catch (final SchedulerException e) {
			throw new IllegalStateException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		final JobDetail jobDetail = QuartzAdapter.getSingleton().getContextJobDetail(context);
		final String jobFullName = QuartzAdapter.getSingleton().getJobFullName(jobDetail);
		/*JOB_COUNTER.bindContextIncludingCpu(jobFullName);*/
	}

	/** {@inheritDoc} */
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		/*JOB_COUNTER.unbindContext();*/
	}

	/** {@inheritDoc} */
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// sera recalculé: final long jobRunTime = context.getJobRunTime();
		final String stackTrace;
		if (jobException == null) {
			stackTrace = null;
		} else {
			final StringWriter stackTraceWriter = new StringWriter(200);
			jobException.printStackTrace(new PrintWriter(stackTraceWriter));
			stackTrace = stackTraceWriter.toString();
		}
	
	/*	JOB_COUNTER.addRequestForCurrentContext(stackTrace);*/
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getClass().getName();
	}
}
