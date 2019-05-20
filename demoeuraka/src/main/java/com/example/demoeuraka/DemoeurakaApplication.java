package com.example.demoeuraka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DemoeurakaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoeurakaApplication.class, args);
	}

}
