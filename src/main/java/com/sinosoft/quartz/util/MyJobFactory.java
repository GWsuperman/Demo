package com.sinosoft.quartz.util;

/** 
 *
 * @author  Ma_Wenzheng
 * @date 创建时间：2016年9月26日 上午9:30:24 
 */
import org.apache.log4j.Logger;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

public class MyJobFactory extends AdaptableJobFactory {
	// 这个对象Spring会帮我们自动注入进来,也属于Spring技术范畴.
	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;
	private Logger log = Logger.getLogger(MyJobFactory.class);

	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		// 调用父类的方法
		Object jobInstance = super.createJobInstance(bundle);
		// 进行注入,这属于Spring的技术,不清楚的可以查看Spring的API.
		capableBeanFactory.autowireBean(jobInstance);
		return jobInstance;
	}
}
