package kr.co.ch10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest
class Ch10ApplicationTests {

	@DisplayName("1. 출력 테스트")
	@Test
	void contextLoads() {
		System.out.println("테스트 실행...");
	}

}
