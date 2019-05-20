package com.example.demofeign;

import com.example.demofeign.apiService.ApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemofeignApplicationTests {

	@Autowired
	private ApiService apiService;
	@Test
	public void contextLoads() {
		try {
			System.out.println("-------------------"+apiService.haha("1"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
