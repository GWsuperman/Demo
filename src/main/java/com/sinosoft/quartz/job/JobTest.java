package com.sinosoft.quartz.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.quartz.dto.JobDto;
import com.sinosoft.quartz.services.impl.TestServiceImpl;

/**
 *
 * @author Ma_Wenzheng
 * @date 创建时间：2016年9月26日 上午9:24:50
 */
public class JobTest implements Job {
	@Autowired
	private TestServiceImpl testService;
	
	private Logger log=Logger.getLogger(JobTest.class);

	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		log.debug("任务成功运行");
		JobDto detailInfo = (JobDto) jec.getMergedJobDataMap().get("scheduleJob");
		log.debug("任务名称 = [" + detailInfo.getJob_name() + "]");
		if (testService == null) {
			log.info("------注入不成功------");
		} else {
			log.info("------注入成功------");
			testService.add();
		}
	}
}
