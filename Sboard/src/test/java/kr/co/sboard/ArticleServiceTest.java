package kr.co.sboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.sboard.service.ArticleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ArticleServiceTest {
	
	@Autowired
	private ArticleService service;
	
	//@Test
	void insertTest() {
		
		boolean bol = Pattern.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", "ooo3345@naver.com.kr");
		log.info(""+bol);
		assertEquals(bol, true);
	}
}
