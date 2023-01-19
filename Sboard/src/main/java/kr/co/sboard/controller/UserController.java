package kr.co.sboard.controller;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import kr.co.sboard.service.EmailService;
import kr.co.sboard.service.UserService;
import kr.co.sboard.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/login")
	@PreAuthorize("isAnonymous()")
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
	public Map<String, String> checkUid(@RequestBody Map<String, String> map) {
		Integer result = service.countByUid(map.get("uid"));
		log.info(result.toString());
		map.put("result", result.toString());
		return map;
	}
	
	@PostMapping("/checkEmail")
	@ResponseBody
	public Map<String, Object> checkEmail(@RequestBody Map<String, Object> map) {
		emailService.send(map);
		log.info(map.toString());
		return map;
	}
	
	@GetMapping("/auth")
	@ResponseBody
	public Object auth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	
	
}
