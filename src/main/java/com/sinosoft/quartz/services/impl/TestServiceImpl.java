package com.sinosoft.quartz.services.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sinosoft.quartz.services.TestService;

/** 
 *
 * @author  Ma_Wenzheng
 * @date 创建时间：2016年9月26日 上午9:26:07 
 */
@Service("testService")
public class TestServiceImpl  implements TestService{
	private Logger log = Logger.getLogger(JobServiceImpl.class);
	public void add(){
		log.info("----注入测试----");
	}
}



