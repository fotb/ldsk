package com.noway.ldsk.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class SpringUtil {

	private static final Logger logger = Logger.getLogger(SpringUtil.class);
	
	protected static final String BEAN_FACTORY_NAME = "com.report";
	
	private static BeanFactory factory = SingletonBeanFactoryLocator.getInstance().useBeanFactory(BEAN_FACTORY_NAME).getFactory();
	
	/**
	 * Get Bean Object
	 * @param beanName bean Name
	 * @return Bean Object
	 */
	public static Object getBean(String beanName) {
		Object bean = null;
		
		try {
			bean = factory.getBean(beanName);
		} catch(BeansException be) {
			logger.error("Failed to obtain bean [" + beanName + "]", be);			
		}
		
		return bean;
	}
}
