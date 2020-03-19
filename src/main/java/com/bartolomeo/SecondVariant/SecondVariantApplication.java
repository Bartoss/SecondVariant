package com.bartolomeo.SecondVariant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SecondVariantApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondVariantApplication.class, args);
	}

}
