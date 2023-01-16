package kr.co.ch08.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	public void loginSuccess(Model model, Authentication authentication) {
		
		if(authentication != null) { // null이라는 것은 로그인 인증을 받지 않은 사용자라는 것
			UserDetails user = (UserDetails)authentication.getPrincipal(); // User의 정보를 가져옴
			
			model.addAttribute("user", service.selectUser2(user.getUsername()));
			model.addAttribute("roles", user.getAuthorities().toString().replaceAll("(\\[|\\])", ""));
		}
	}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(User2VO vo) {
		service.insertUser2(vo);
		return "redirect:/user2/login";
	}
	
	
	
}
