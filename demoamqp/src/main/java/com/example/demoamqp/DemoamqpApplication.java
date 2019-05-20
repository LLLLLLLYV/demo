package com.example.demoamqp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan({"com.example.demoamqp.DeadAmqpController.*"})
public class DemoamqpApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoamqpApplication.class, args);
	}

}
