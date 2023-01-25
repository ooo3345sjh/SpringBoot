package kr.co.sboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.sboard.service.UserService;
import kr.co.sboard.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class UserServiceTest {
	
	@Autowired
	private UserService service;
	
//	@Test
	void insertTest() {
		
		boolean bol = Pattern.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", "ooo3345@naver.com.kr");
		log.info(""+bol);
		assertEquals(bol, true);
	}
	
//	@Test
	void selecgtTest() {
		List<UserVO> list = service.getUserAll();
		log.info(list.toString());
		assertEquals( list.toString().contains("uid=a101"), true);
	}
	
	@Test
	void fieldDuplicateCheckTest() {
		List<UserVO> users = service.getUserAll();
		
	}
}
