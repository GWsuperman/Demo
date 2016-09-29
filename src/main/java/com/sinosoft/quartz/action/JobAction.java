package com.sinosoft.quartz.action;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinosoft.quartz.dto.JobDto;
import com.sinosoft.quartz.services.JobService;

/**
 * job Action 与页面交互的类
 * 
 * @author Ma_Wenzheng
 * @date 创建时间：2016年9月26日 下午2:48:59
 */
@Controller("/job/*")
public class JobAction {
	private Logger log = Logger.getLogger(JobAction.class);
	@Resource
	private JobService jobService;

	/**
	 * 手动添加一个任务
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("addJob.do")
	public String addJob(ServletRequest request, ServletResponse response,
			JobDto dto) {
		log.debug("进入addJob方法");
		request.setAttribute("addJob", "failure");
		if (dto!=null && dto.getJob_group()!=null && dto.getJob_name()!=null) {
			log.debug("Job_id:" + dto.getJob_id());
			log.debug("Job_name:" + dto.getJob_name());
			log.debug("Job_group:" + dto.getJob_group());
			log.debug("Job_time:" + dto.getJob_time());
			if (jobService.addJob(dto)) {
				request.setAttribute("addJob", "success");
			}
		} else {
			log.error("job’s Dto is null.");
		}
		return "/../index";
	}

	/**
	 * 删除一个任务<br>
	 * 根据job_group & job_name
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("removeJob.do")
	public String removeJob(ServletRequest request, ServletResponse response,
			JobDto dto) {
		log.debug("进入removeJob方法");
		request.setAttribute("removeJob", "failure");
		if (dto!=null && dto.getJob_group()!=null && dto.getJob_name()!=null) {
			if (jobService.removeJob(dto)) {
				log.info("任务删除成功");
				log.debug("Job_group:" + dto.getJob_group() + ";Job_name:"
						+ dto.getJob_name());
				request.setAttribute("removeJob", "success");
			}
		} else {
			log.error("job’s Dto is null.");
		}
		return "/../index";
	}
	/**
	 * 暂停一个任务<br>
	 * 根据job_group & job_name
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("pauseJob.do")
	public String pauseJob(ServletRequest request, ServletResponse response,
			JobDto dto) {
		log.debug("进入pauseJob方法");
		request.setAttribute("pauseJob", "failure");
		if(dto!=null && dto.getJob_group()!=null && dto.getJob_name()!=null){
			dto.setJob_stat("0");
			if(jobService.pauseJob(dto)){
				log.info("一个任务暂停成功");
				request.setAttribute("pauseJob", "success");
			}else{
				log.error(" 暂停一个任务 失败");
			}
		}else {
			log.error("job’s Dto is null.");
		}
		
		return "/../index";
	}
	/**
	 * 重启一个任务<br>
	 * 根据job_group & job_name
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("resumeJob.do")
	public String resumeJob(ServletRequest request, ServletResponse response,
			JobDto dto) {
		log.debug("进入resumeJob方法");
		request.setAttribute("resumeJob", "failure");
		if(dto!=null && dto.getJob_group()!=null && dto.getJob_name()!=null){
			dto.setJob_stat("1");
			if(jobService.resumeJob(dto)){
				request.setAttribute("resumeJob", "success");
				log.info("任务重启成功");
			}
		}else {
			log.error("job’s Dto is null.");
		}
		
		return "/../index";
	}
	/**
	 * 暂停所有任务
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("pauseJobs.do")
	public String pauseJobs(ServletRequest request, ServletResponse response){
		log.debug("暂停所有任务action");
		request.setAttribute("pauseJobs", "failure");
		if(jobService.pauseJobs()){
			log.info("暂停所有任务成功。");
			request.setAttribute("pauseJobs", "success");
		}
		return "/../index";
	}
	/**
	 * 启动所有任务
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("resumeJobs.do")
	public String resumeJobs(ServletRequest request, ServletResponse response){
		log.debug("启动所有任务action");
		request.setAttribute("resumeJobs", "failure");
		if(jobService.resumeJobs()){
			log.info("启动所有任务成功");
			request.setAttribute("resumeJobs", "success");
		}
		return "/../index";
	}
}
