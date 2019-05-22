package com.example.demoamqp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan({"com.example.demoamqp.DeadAmqpController.*"})
public class DemoamqpApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoamqpApplication.class, args);
	}

}
