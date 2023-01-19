package kr.co.sboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	@Test
	void insertTest() {
		
		//service.fileUpload();
	}
}
