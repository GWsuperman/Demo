package com.sinosoft.quartz.services;

import org.quartz.SchedulerException;

import com.sinosoft.quartz.dto.JobDto;

/**
 * 任务调度，quartz业务层
 * 
 * @author Ma_Wenzheng
 * @date 创建时间：2016年9月26日 上午11:17:45
 */
public interface JobService {
	/**
	 * 系统启动是初始化加载数据库中所有任务
	 * 
	 * @throws SchedulerException
	 */
	public void loadJobInit() throws SchedulerException;

	/**
	 * 系统启动后，手动添加任务
	 * 
	 * @param dto
	 *            任务实体类
	 * @return
	 */
	public boolean addJob(JobDto dto);

	/**
	 * 手动删除任务<br>
	 * 删除调度器中的任务<br>
	 * 删除数据库中的数据
	 * 
	 * @param dto
	 * @return
	 */
	public boolean removeJob(JobDto dto);

	/**
	 * 暂停一个已启动的任务<br>
	 * 暂停调度器中的任务<br>
	 * 修改数据库中的数据状态为暂停
	 */
	public boolean pauseJob(JobDto dto);

	/**
	 * 重启一个暂停的任务<br>
	 * 重启调度器中的任务<br>
	 * 修改数据库中的数据状态为启动
	 */
	public boolean resumeJob(JobDto dto);

	/**
	 * 暂停所有已启动的任务<br>
	 * 暂停调度器中的任务<br>
	 * 修改数据库中的数据状态为暂停
	 */
	public boolean pauseJobs();

	/**
	 * 重启所有暂停的任务<br>
	 * 重启调度器中的任务<br>
	 * 修改数据库中的数据状态为启动
	 */
	public boolean resumeJobs();
}
