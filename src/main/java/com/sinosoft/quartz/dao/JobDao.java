package com.sinosoft.quartz.dao;

import java.sql.ResultSet;

import com.sinosoft.quartz.dto.JobDto;

/** 
 * job_quartz 表的操作类
 * @author  Ma_Wenzheng
 * @date 创建时间：2016年9月26日 上午10:36:42 
 */
public interface JobDao {
	public ResultSet getList();
	/**
	 * 向数据库中插入一条数据
	 * @param job
	 * @return
	 */
	public int add(JobDto job);
	/**
	 * 从数据库中删除一条数据
	 * @param job
	 * @return
	 */
	public int remove(JobDto job);
	/**
	 * 根据job_group & job_name 修改任务状态
	 * @param job
	 * @param isAll 是否修改所有数据
	 * @return
	 */
	public int modify(JobDto job,boolean isAll);
}


