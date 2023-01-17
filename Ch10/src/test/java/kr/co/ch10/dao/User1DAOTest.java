package kr.co.ch10.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.ch10.vo.User1VO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class User1DAOTest {

	@Autowired
	private User1DAO dao;
	
	@Test
	public void DbTest() {
		
		// 삭제
		int result = dao.deleteUser1("g102");
		
		
		// 추가
		User1VO vo = User1VO.builder()
				.uid("g102").name("홍길동").hp("010-1234-3333").age(32).build();
		result = dao.insertUser1(vo);
		assertEquals(result, 1);
		
		// 조회
		User1VO vo1 = dao.selectUser1("g1123102");
		assertEquals(vo.getUid(), vo1.getUid());
		
		log.info(vo.toString());
		log.info(vo1.toString());
	}
	
}
