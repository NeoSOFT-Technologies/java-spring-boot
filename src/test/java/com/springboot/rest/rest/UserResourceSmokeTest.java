package com.springboot.rest.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserResourceSmokeTest {

	@Autowired
	private UserResource uRes;
	

	@Test
	void contextLoads() {
		assertThat(uRes).isNotNull();
	}
	
	
}
