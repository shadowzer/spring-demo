package com.example.demo.springDupper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cod_s on 10.02.2018.
 */
@Component
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
	private Map<String, Class> map = new HashMap<>();
	private ProfilingController controller = new ProfilingController();

	public ProfilingHandlerBeanPostProcessor() throws Exception {
		MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
		platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Class<?> beanClass = bean.getClass();
		if (beanClass.isAnnotationPresent(Profiling.class)) {
			map.put(beanName, beanClass);
		}
		return bean;
	}

	// между этими двумя методами вызывается init метод:
	// @PostConstruct (Annotation)
	// init-method (XML)
	// afterPropertiesSet (implements InitializingBean)

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class beanClass = map.get(beanName);
		if (beanClass != null)
			return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
				if (controller.isEnabled()) {
					System.out.println("PROFILING...");
					long before = System.nanoTime();
					Object result = method.invoke(bean, args);
					long after = System.nanoTime();
					System.out.println(after - before + " nanoseconds");
					return result;
				} else {
					return method.invoke(bean, args);
				}
			});
		return bean;
	}
}
