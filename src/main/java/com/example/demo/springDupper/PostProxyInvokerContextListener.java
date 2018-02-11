package com.example.demo.springDupper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by cod_s on 10.02.2018.
 */
@Component
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private ConfigurableListableBeanFactory factory;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext context = event.getApplicationContext();
		for (String name : context.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = factory.getBeanDefinition(name);
			String originalClassName = beanDefinition.getBeanClassName();
			if (originalClassName != null) {
				try {
					Class<?> originalClass = Class.forName(originalClassName);
					for (Method method : originalClass.getMethods()) {
						if (method.isAnnotationPresent(PostProxy.class)) {
							Object bean = context.getBean(name);
							Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
							currentMethod.invoke(bean);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
