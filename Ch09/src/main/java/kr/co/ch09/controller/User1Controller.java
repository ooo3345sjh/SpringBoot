package kr.co.ch09.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ch09.service.User1Service;
import kr.co.ch09.vo.User1VO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class User1Controller {
	
	@Autowired
	private User1Service service;
	
	@GetMapping("/user1")
	public List<User1VO> list1() throws Exception {
		List<User1VO> users = service.selectUser1s();
		return users;
	}
	
	@GetMapping("/user1/{id}")
	public User1VO list2(@PathVariable("id") String uid) throws Exception {
		return service.selectUser1(uid);
	}
	
	@PostMapping("/user1")
	public int register(User1VO vo) throws Exception {
		return service.insertUser1(vo);
	}
	
	// 해당 컨트롤러에서 발생하는 모든 Exception에 대한 에러를 처리하는 메서드(@Controller, @RestController에서만 적용가능)
	@ExceptionHandler(Exception.class)
	public int failCrud(Exception e) {
		log.error("Unexpected error occurred : {}", e.getMessage(), e);
		return -9999;
	}
	
	@PutMapping("/user1")
	public int modify(User1VO vo) throws Exception {
		return service.updateUser1(vo);
	}

	@DeleteMapping("/user1/{id}")
	public int delete(@PathVariable("id") String uid) throws Exception {
		return service.deleteUser1(uid);
	}

}
