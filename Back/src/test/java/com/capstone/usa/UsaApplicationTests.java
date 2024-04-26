package com.capstone.usa;

import com.capstone.usa.service.VerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UsaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testGenerateNumber() {
        String generatedNumber = VerificationService.GenerateNumber();

		assertEquals(4, generatedNumber.length());

		for (char digit : generatedNumber.toCharArray()) {
			assertTrue(Character.isDigit(digit));
		}

		System.out.println("Generated number: " + generatedNumber);
	}

}
