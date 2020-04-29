package com.commerce.ach;

import com.commerce.ach.storage.StorageProperties;
import com.commerce.ach.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ACHApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ACHApplication.class, args);
		FileWatch.run();
		ACHRoutes.run();
	}

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
