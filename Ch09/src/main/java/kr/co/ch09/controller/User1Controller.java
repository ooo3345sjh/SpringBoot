package kr.co.ch09.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ch09.service.User1Service;
import kr.co.ch09.vo.User1VO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", allowedHeaders = "*")
public class User1Controller {
	
	@Autowired
	private User1Service service;
	
	@GetMapping("/user1s")
	public List<User1VO> list1() throws Exception {
		List<User1VO> users = service.selectUser1s();
		return users;
	}
	
	@GetMapping("/user1")
	public User1VO list2(String uid) throws Exception {
		log.debug("list2 start...");
		log.debug("uid ： " + uid);
		return service.selectUser1(uid);
	}
	
	@GetMapping("/user1/{id}")
	public User1VO list3(@PathVariable("id") String uid) throws Exception {
		return service.selectUser1(uid);
	}
	
	@PostMapping("/user1")
	public Map register(@RequestBody User1VO vo) throws Exception {
		log.debug(vo.toString());
		int result = service.insertUser1(vo);
		Map map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	// 해당 컨트롤러에서 발생하는 모든 Exception에 대한 에러를 처리하는 메서드(@Controller, @RestController에서만 적용가능)
	@ExceptionHandler(Exception.class)
	public int failCrud(Exception e) {
		log.error("Unexpected error occurred : {}", e.getMessage(), e);
		return -9999;
	}
	
	@PutMapping("/user1")
	public Map modify(@RequestBody User1VO vo) throws Exception {
		Map map = new HashMap<>();
		map.put("result", service.updateUser1(vo));
		return map;
	}

	@DeleteMapping("/user1/{id}")
	public Map delete(@PathVariable("id") String uid) throws Exception {
		Map map = new HashMap<>();
		map.put("result", service.deleteUser1(uid));
		return map;
	}

}
