package com.example.demo.springDupper;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by cod_s on 10.02.2018.
 */
@Component
@Profiling
@DeprecatedClass(newImpl = T1000.class)
public class TerminatorQuoter implements Quoter {
	@InjectRandomInt(min = 2, max = 4)
	private int repeat;
	private String message = "I'll be back";

	public TerminatorQuoter() {
		System.out.println("Constructor (phase 1): " + repeat); // 0 потому что Spring сначала создает объект, а потом уже его настраивает => аннотация на repeat из BPP еще не успела сработать
	}

	@PostConstruct
	public void init() {
		System.out.println("@PostConstruct (phase 2): " + repeat); // здесь repeat уже будет задан [2, 4], так как Spring к этому времени уже успел всё настроить
	}

	@Override
	@PostProxy
	public void sayQuote() {
		System.out.println("@PostProxy (phase 3): profiling should working now");
		for (int i = 0; i < repeat; i++) {
			System.out.println(message);
		}
	}
}
