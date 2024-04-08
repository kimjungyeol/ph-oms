package com.ktnet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktnet.fta.sample.service.SampleService;

@SpringBootTest
class FtaServiceApplicationTests {
	
	@Autowired
    SampleService sampleService;

	@Test
	void contextLoads() {
		System.out.println("111");
	}
}
