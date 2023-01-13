package kr.co.ch08.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ch08.service.User2Service;
import kr.co.ch08.vo.User2VO;


@Controller
@RequestMapping("/user2")
public class User2Controller {
	
	@Autowired
	private User2Service service;
	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess(Principal principal, Model model) {
		User2VO vo = null;
		
		if(principal != null) 
			vo = service.selectUser2(principal.getName());
		
		model.addAttribute("user", vo);
	}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(User2VO vo) {
		service.insertUser2(vo);
		return "redirect:/user2/login";
	}
	
	
	
}
