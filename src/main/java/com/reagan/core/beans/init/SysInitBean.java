package com.reagan.core.beans.init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.reagan.core.util.PropertiesUtil;
import com.reagan.core.util.SysXmlConfig;
import com.reagan.util.LocalCached;


/**
 * <p>Description: 系统初始化Bean的时候调用处理</p>
 * @date 2013年9月2日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component
public class SysInitBean implements BeanPostProcessor{
	
	/** 
	 * 方法用途: 初始BEAN前调用<br>
	 * 实现步骤: 在创建BEAN之前调用该方法<br>
	 * @param bean 对象
	 * @param beanName 名字
	 * @return 创建号的BEAN
	 * @throws BeansException   
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/** 
	 * 方法用途: 初始BEAN后调用<br>
	 * 实现步骤: 在创建BEAN之后调用该方法<br>
	 * @param bean 对象
	 * @param beanName 名字
	 * @return 创建号的BEAN
	 * @throws BeansException   
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		PropertiesUtil.init("sysconfig.properties");
		String dataSwitch = PropertiesUtil.getProperty("sys.data.config.switch");
		LocalCached.add("sys.data.path", PropertiesUtil.getProperty("sys.data.path"));
		LocalCached.add("sys.data.config.switch", dataSwitch);
		if(dataSwitch.equals("on")) {
			if(SysXmlConfig.getConfigInfo() == null) {
				SysXmlConfig.SysConfigInit();
			}
		}
		return bean;
	}

}
