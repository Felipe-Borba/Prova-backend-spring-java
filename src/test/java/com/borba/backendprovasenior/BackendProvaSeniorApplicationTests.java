package com.borba.backendprovasenior;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class BackendProvaSeniorApplicationTests {

	@Test
	void contextLoads() {
		assertEquals("5.3.19", SpringVersion.getVersion());
	}

}
