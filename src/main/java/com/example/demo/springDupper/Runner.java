package com.example.demo.springDupper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
	@Autowired
	private ApplicationContext context;

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("-------- RUNNING --------");
		context.getBean(Quoter.class).sayQuote();
	}
}
