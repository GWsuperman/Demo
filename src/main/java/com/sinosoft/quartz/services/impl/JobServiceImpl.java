package com.sinosoft.quartz.services.impl;

/** 
 *
 * @author  Ma_Wenzheng
 * @date 创建时间：2016年9月26日 上午9:28:15 
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.sinosoft.quartz.dao.JobDao;
import com.sinosoft.quartz.dto.JobDto;
import com.sinosoft.quartz.job.JobTest;
import com.sinosoft.quartz.services.JobService;
import com.sinosoft.quartz.util.JobManager;

/**
 * 调用job
 * 
 * @author Administrator
 *
 */
@Service("jobService")
public class JobServiceImpl implements JobService {
	@Resource
	private JobManager jobManager;
	@Resource
	private JobDao jobDao;
	private Logger log = Logger.getLogger(JobServiceImpl.class);

	public JobManager getJobManager() {
		return jobManager;
	}

	public void setJobManager(JobManager jobManager) {
		this.jobManager = jobManager;
	}

	/**
	 * 初始化定时任务
	 * 
	 * @throws SchedulerException
	 */
	public void loadJobInit() throws SchedulerException {
		log.debug("---loadJobInit-初始化定时任务--");
		if (jobDao != null) {
			ResultSet rs = jobDao.getList();
			try {
				JobDto dto;
				while (rs.next()) {
					dto = new JobDto();
					dto.setJob_id(rs.getString("job_id"));
					dto.setJob_name(rs.getString("job_name"));
					dto.setJob_group(rs.getString("job_group"));
					dto.setJob_time(rs.getString("job_time"));
					jobManager.addJob(dto, JobTest.class);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			log.error("jobDao init failure.");
		}
		jobManager.startJobs();
	}

	/**
	 * 将任务添加到运行的调度器中,并且向数据库中插入一条数据
	 */
	public boolean addJob(JobDto dto) {
		log.debug("添加job Service.");
		try {
			log.debug("将任务添加至调度器");
			jobManager.addJob(dto, JobTest.class);
		} catch (SchedulerException e) {
			log.info("任务添加失败，将任务加入调度器时出现问题");
			e.printStackTrace();
			return false;
		}
		if (jobDao.add(dto) != 1) {
			log.info("任务添加失败，插入数据库时出现问题");
			log.debug("根据jobkey，删除刚添加的任务");
			jobManager.stopJob(JobKey.jobKey(dto.getJob_name(),
					dto.getJob_group()));
			return false;
		}
		log.info("任务添加成功");
		return true;
	}

	public boolean removeJob(JobDto dto) {
		log.debug("删除任务 service .");
		if (jobManager.stopJob(JobKey.jobKey(dto.getJob_name(),
				dto.getJob_group()))) {
			log.debug("成功将任务从调度器中删除，开始删除数据库中的数据");
			if (jobDao.remove(dto) == 1) {
				log.info("删除任务成功");
				return true;
			}
		}
		return false;
	}

	public boolean pauseJob(JobDto dto) {
		log.debug("暂停一个任务 service .");
		if (jobManager.pauseJob(JobKey.jobKey(dto.getJob_name(),
				dto.getJob_group()))) {
			log.debug("调度器中任务已成功暂停，开始修改数据库状态");
			if (jobDao.modify(dto, false) == 1) {
				log.debug("数据库中数据修改成功");
				log.info("任务暂停成功");
				return true;
			}
		}
		return false;
	}

	public boolean resumeJob(JobDto dto) {
		log.debug("重启一个任务 service .");
		if (jobManager.resumeJob(JobKey.jobKey(dto.getJob_name(),
				dto.getJob_group()))) {
			log.debug("重启成功，修改数据库中的状态");
			if (jobDao.modify(dto, false) == 1) {
				log.debug("数据库中数据修改成功");
				log.info("任务暂停成功");
				return true;
			}
		}
		return false;
	}

	public boolean pauseJobs() {
		log.debug("暂停所有任务 service .");
		if (jobManager.pauseJobs()) {
			log.debug("重启成功，修改数据库中的状态");
			if (jobDao.modify(new JobDto("0"), true) > 0) {
				log.debug("数据库中数据修改成功");
				log.info("任务暂停成功");
				return true;
			}
		}
		return false;
	}

	public boolean resumeJobs() {
		log.debug("重启所有任务  service .");
		if (jobManager.resumeJobs()) {
			log.debug("重启成功，修改数据库中的状态");
			if (jobDao.modify(new JobDto("1"), true) > 0) {
				log.debug("数据库中数据修改成功");
				log.info("任务重启成功");
				return true;
			}
		}
		return false;
	}
}
