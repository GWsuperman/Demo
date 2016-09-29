package com.sinosoft.quartz.dto;

/**
 * job_quartz 表的实体类
 * 
 * @author Ma_Wenzheng
 * @date 创建时间：2016年9月26日 上午9:24:17
 */
public class JobDto {
	private String job_id;
	private String job_name;
	private String job_group;
	private String job_time;
	private String job_stat;

	/******** 任务状态 ************/
	/**
	 * 任务状态<br>
	 * 已启动
	 */
	public static final String job_stat_1 = "1";
	/**
	 * 任务状态<br>
	 * 已暂停
	 */
	public static final String job_stat_0 = "0";

	public String getJob_id() {
		return job_id;
	}

	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}

	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public String getJob_group() {
		return job_group;
	}

	public void setJob_group(String job_group) {
		this.job_group = job_group;
	}

	public String getJob_time() {
		return job_time;
	}

	public void setJob_time(String job_time) {
		this.job_time = job_time;
	}

	public String getJob_stat() {
		return job_stat;
	}

	public void setJob_stat(String job_stat) {
		this.job_stat = job_stat;
	}

	public JobDto(String job_stat) {
		super();
		this.job_stat = job_stat;
	}

	public JobDto() {
		super();
	}
}
