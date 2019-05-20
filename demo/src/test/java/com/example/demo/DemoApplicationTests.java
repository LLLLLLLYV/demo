package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaConfig.class);
		KafkaTemplate<Integer, String> kafkaTemplate = (KafkaTemplate<Integer, String>) ctx.getBean("kafkaTemplate");
		String data="hxxxxxxxxxxxxxxxxxxxxx";
		ListenableFuture<SendResult<Integer, String>> send = kafkaTemplate.send("test", 1, data);
		send.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			public void onFailure(Throwable throwable) {

			}

			public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
				System.out.println(("success"));
			}
		});
	}

}
