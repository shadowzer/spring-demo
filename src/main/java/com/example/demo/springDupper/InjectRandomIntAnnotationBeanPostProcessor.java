package com.example.demo.springDupper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Random;

@Component
public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
			if (annotation != null) {
				System.out.println("FOUND CLASS WITH ANNOTATION InjectRandomInt: " + bean.getClass().getTypeName());
				int min = annotation.min();
				int max = annotation.max();
				Random random = new Random();
				int i = min + random.nextInt(max - min);
				field.setAccessible(true);
				ReflectionUtils.setField(field, bean, i);
			}
		}

		return bean;
	}

	// между этими двумя методами вызывается init метод:
	// @PostConstruct (Annotation)
	// init-method (XML)
	// afterPropertiesSet (implements InitializingBean)

	@Override
	public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
		return bean;
	}
}
