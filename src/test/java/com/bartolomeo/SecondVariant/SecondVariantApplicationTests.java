package com.bartolomeo.SecondVariant;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =  SecondVariantApplication.class)
@WebAppConfiguration
class SecondVariantApplicationTests {

	@Test
	void contextLoads() {
		//check that app load context
		assertTrue(true);
	}

}
