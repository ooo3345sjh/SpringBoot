package kr.co.sboard.controller;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sboard.service.UserService;
import kr.co.sboard.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(UserVO vo, HttpServletRequest req) {
		int result = service.userInsert(vo, req);
		return "redirect:/user/login";
	}
	
	@GetMapping("/terms")
	public void terms(Model model) {
		model.addAttribute("termsDTO", service.selectTerms());
	}
	
	@PostMapping("/checkUid")
	@ResponseBody
	public Map<String, Object> checkUid(@RequestParam String uid) {
		log.info(uid);
		Map<String, Object> map = new HashMap<>();
		map.put("result", 2);
		return map;
	}
	
	@GetMapping("/auth")
	@ResponseBody
	public Object auth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	
	
}
