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

@RestController
public class User1Controller {
	
	@Autowired
	private User1Service service;
	
	@GetMapping("/user1")
	public List<User1VO> list1() {
		List<User1VO> users = service.selectUser1s();
		return users;
	}
	
	@GetMapping("/user1/{id}")
	public User1VO list2(@PathVariable("id") String uid) {
		return service.selectUser1(uid);
	}
	
	@PostMapping("/user1")
	public int register(User1VO vo) {
		return service.insertUser1(vo);
	}
	
	@PutMapping("/user1")
	public int modify(User1VO vo) {
		return service.updateUser1(vo);
	}

	@DeleteMapping("/user1/{id}")
	public int delete(@PathVariable("id") String uid) {
		return service.deleteUser1(uid);
	}

}
