package com.sinosoft.quartz.util;

/** 
 *
 * @author  Ma_Wenzheng
 * @date 创建时间：2016年9月26日 上午9:27:01 
 */
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

import com.sinosoft.quartz.dto.JobDto;

@Component("jobManager")
public class JobManager {
	@Resource
	private Scheduler scheduler;
	private Logger log = Logger.getLogger(JobManager.class);

	/**
	 * 添加一个定时任务
	 * @param job 任务实体类
	 * @param classz 对应的job类
	 */
	public void addJob(JobDto job, Class classz) throws SchedulerException {
		log.debug("添加定时任务:job.group=" + job.getJob_group() + ";job_name="
				+ job.getJob_name());
		// 根据数据库中任务的分组和名称创建唯一的触发器的key，为每一个任务创建唯一的触发器
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJob_name(),
				job.getJob_group());
		// 定义以表达式定义触发时间的触发时间表
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if (trigger == null) {
			log.debug("----trigger is null----");
			// 定义以group&name为唯一标识
			JobDetail jobDetail = JobBuilder.newJob(classz)
					.withIdentity(job.getJob_name(), job.getJob_group())
					.build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getJob_time());
			// 按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(job.getJob_name(), job.getJob_group())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getJob_time());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 启动所有定时任务
	 */
	public void startJobs() {
		log.debug("启动所有定时任务");
		try {
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 停止所有任务
	 */
	public void stopJobs() {
		log.debug("停止所有任务");
		try {
			scheduler.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 停止一个任务
	 * @param jobKey 由group和name生成
	 */
	public boolean stopJob(JobKey jobKey) {
		log.debug("停止一个任务");
		boolean flag = false;
		try {
			flag = scheduler.deleteJob(jobKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return flag;
	}

	/**
	 * 暂停一个已启动的任务
	 * @param jobKey 由group和name生成
	 */
	public boolean pauseJob(JobKey jobKey) {
		log.debug("暂停一个已启动的任务");
		log.debug("group:" + jobKey.getGroup() + ";name:" + jobKey.getName());
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			log.error("暂停一个已启动的任务出现问题：" + e.getMessage());
			return false;
		}
		log.debug("暂停一个已启动的任务成功。");
		return true;
	}

	/**
	 * 启动一个暂停的任务
	 * @param jobKey 由group和name生成
	 */
	public boolean resumeJob(JobKey jobKey) {
		log.debug("启动一个暂停的任务");
		log.debug("group:" + jobKey.getGroup() + ";name:" + jobKey.getName());
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			log.error("启动一个暂停的任务出现问题：" + e.getMessage());
			return false;
		}
		log.debug("启动一个暂停的任务成功。");
		return true;
	}

	/**
	 * 暂停所有已启动的任务
	 */
	public boolean pauseJobs() {
		log.debug("暂停所有已启动的任务");
		try {
			scheduler.pauseAll();
		} catch (SchedulerException e) {
			log.error("暂停所有已启动的任务出现问题：" + e.getMessage());
			return false;
		}
		log.debug("暂停所有已启动的任务成功。");
		return true;
	}

	/**
	 * 启动所有暂停的任务
	 */
	public boolean resumeJobs() {
		log.debug("启动所有暂停的任务");
		try {
			scheduler.resumeAll();
		} catch (SchedulerException e) {
			log.error("启动所有暂停的任务出现问题：" + e.getMessage());
			return false;
		}
		log.debug("启动所有暂停的任务成功。");
		return true;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
}
