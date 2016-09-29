package com.sinosoft.quartz.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sinosoft.quartz.dao.JobDao;
import com.sinosoft.quartz.dto.JobDto;
import com.sinosoft.quartz.util.jdbc.BaseDao;
import com.sinosoft.quartz.util.jdbc.impl.BaseDaoImpl;

/**
 * job_quartz 表的dao层
 * @author Ma_Wenzheng
 * @date 创建时间：2016年9月26日 上午10:36:54
 */
@Repository("jobDao")
public class JobDaoImpl implements JobDao {
	private Logger log = Logger.getLogger(JobDaoImpl.class);
	private BaseDao baseDao = new BaseDaoImpl();

	@Override
	public ResultSet getList() {
		log.info("查询数据库中的数据");
		ResultSet resultSet = null;
		try {
			Connection conn = baseDao.getConnection();
			if (conn != null) {
				String sql = "select * from job_quartz";
				log.debug("sql:" + sql);
				Statement stat = conn.createStatement();
				resultSet = stat.executeQuery(sql);
			} else {
				log.error("获取链接失败.");
			}
		} catch (SQLException e) {
			log.error("执行sql出现错误，错误信息为：" + e.getMessage());
		}
		return resultSet;
	}

	@Override
	public int add(JobDto job) {
		int l = 0;
		try {
			String sql = "insert into job_quartz   (job_id, job_name, job_group, job_time) values  "
					+ "('"
					+ job.getJob_id()
					+ "', '"
					+ job.getJob_name()
					+ "', '"
					+ job.getJob_group()
					+ "', '"
					+ job.getJob_time()
					+ "')";
			log.debug("sql:" + sql);
			l = baseDao.getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			log.error("添加 job_quartz 表中数据出现问题,job_group:" + job.getJob_group()
					+ ";job_name:" + job.getJob_name());
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public int remove(JobDto job) {
		int i = 0;
		try {
			String sql = "delete from job_quartz s where s.job_group = '"
					+ job.getJob_group() + "' and s.job_name = '"
					+ job.getJob_name() + "'";
			log.debug("sql:" + sql);
			i = baseDao.getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			log.error("删除job_quartz 表中数据出现问题,job_group:" + job.getJob_group()
					+ ";job_name:" + job.getJob_name());
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int modify(JobDto job, boolean isAll) {
		int i = 0;
		String sql = " update job_quartz set ";
		boolean flag = false;
		if (job.getJob_time() != null && !"".equals(job.getJob_time())) {
			flag = true;
			sql += " job_time = '" + job.getJob_time() + "' ";
		}
		if (job.getJob_stat() != null && !"".equals(job.getJob_stat())) {
			if (flag) {
				sql += " , ";
			}
			sql += " job_stat = '" + job.getJob_stat() + "' ";
		}
		sql += " where 1 = 1 ";

		if (!isAll) {
			sql += " and job_group='" + job.getJob_group()
					+ "' and job_name = '" + job.getJob_name() + "' ";
		}
		try {
			log.debug("modify sql:" + sql);
			i = baseDao.getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
}
