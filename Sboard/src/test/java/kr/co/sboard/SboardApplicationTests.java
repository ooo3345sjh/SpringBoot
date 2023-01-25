//package kr.co.sboard;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import kr.co.sboard.dao.UserDAO;
//import kr.co.sboard.repository.UserRepo;
//import kr.co.sboard.service.UserService;
//import kr.co.sboard.vo.UserVO;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@SpringBootTest
//class SboardApplicationTests {
//	
//	@Autowired
//	private UserDAO dao;
//	
//	@Autowired
//	private UserRepo repo;
//
//	@Autowired
//	private UserService service;
//	//@Autowired
//	//private PasswordEncoder bCrypt;
//
//	@Test
//	void insertTest() {
//		//BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(); 
//		UserVO vo = UserVO.builder()
//					.uid("a102")
//					.pass1("1234")
//					.name("홍길동")
//					.nick("홍102")
//					.email("102102@gmai.com")
//					.hp("010-2323-1023")
//					.regip("02391232")
//					.build();
//		
//		// int result = service.userInsert(vo);
//		 
//		int result = 0; 
//		assertEquals(result, 1);
//		 
//		 log.info("result : " + result);
//	}
//	
//	@Test
//	public void countUid() {
//		int result = repo.countUserEntityByUid("a101");
//		
//		assertEquals(1, result);
//		
//	}
//	
//}
