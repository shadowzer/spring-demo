package com.example.demo.springDupper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Created by cod_s on 11.02.2018.
 */
@Component
public class DeprecationHandlerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	// Вызывается еще до того, как создались beans по их beanDefinition.
	// На этом этапе мы можем подменить beanDefinition так, как мы хотим.
	// И уже на основе наших настроенных beanDefinitions beanFactory будет создавать beans.
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		String[] names = beanFactory.getBeanDefinitionNames();
		for (String name : names) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
			String beanClassName = beanDefinition.getBeanClassName();
			try {
				Class<?> beanClass = Class.forName(beanClassName);
				DeprecatedClass annotation = beanClass.getAnnotation(DeprecatedClass.class);
				if (annotation != null) {
					beanDefinition.setBeanClassName(annotation.newImpl().getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
