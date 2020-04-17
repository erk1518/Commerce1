package com.commerce.ach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ACHApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ACHApplication.class, args);
		FileWatch.run();
		ACHRoutes.run();
	}
}
