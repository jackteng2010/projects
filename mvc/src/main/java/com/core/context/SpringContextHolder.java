package com.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 
 * Spring中提供一些Aware相关接口，像是BeanFactoryAware、 ApplicationContextAware、ResourceLoaderAware、ServletContextAware等等，实例这些 Aware接口的Bean在被初始之后，可以取得一些相对应的资源，例如实例BeanFactoryAware的Bean在初始后，Spring容器将会注入BeanFactory的实例，而实例ApplicationContextAware的Bean，在Bean被初始后，将会被注入 ApplicationContext的实例等等。
　* Bean取得BeanFactory、ApplicationContextAware的实例目的是什么，一般的目的就是要取得一些档案资源的存取、相关讯息资源或是那些被注入的实例所提供的机制，例如ApplicationContextAware提供了publishEvent()方法，可以支持基于Observer模式的事件传播机制。
 *
 */
public class SpringContextHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}

	/**
	 * 清除applicationContext静态变量.
	 */
	public static void cleanApplicationContext() {
		applicationContext = null;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
}
