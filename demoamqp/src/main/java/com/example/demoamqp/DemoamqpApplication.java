package com.example.demoamqp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ComponentScan主要就是定义扫描的路径从中找出标识了需要装配的类自动装配到spring的bean容器中
 */
//@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan({"com.example.demoamqp.DeadAmqpController.*"})
@ComponentScan(value = {"com.example.in","com.example.in2","com.example.demoamqp"})
public class DemoamqpApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoamqpApplication.class, args);
	}

}
