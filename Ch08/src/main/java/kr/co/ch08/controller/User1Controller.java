package kr.co.ch08.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ch08.service.User1Service;
import kr.co.ch08.vo.User1VO;

@Controller
@RequestMapping("/user1")
public class User1Controller {
	
	@Autowired
	private User1Service service;
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String login(String uid, String pass, HttpSession session) {
		User1VO user = service.selectUser1(uid, pass);
		
		if(user != null) {
			// 회원이 맞을 경우
			session.setAttribute("sessUser", user);
			return "redirect:/user1/loginSuccess";
		} else {
			// 회원이 아닌 경우
			return "redirect:/user1/login?success=100";
		}
	}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {}
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/user1/login";
	}
	
	

}
