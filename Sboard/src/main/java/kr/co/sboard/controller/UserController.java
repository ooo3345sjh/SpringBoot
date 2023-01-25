package kr.co.sboard.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping("/auth")
	@ResponseBody
	public Object auth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	/*@ResponseBody
	@PostMapping("/checkUid")
	public Map<String, String> checkUid(@RequestBody Map<String, String> map) {
		Integer result = service.countByUid(map.get("uid"));
		map.put("result", result.toString());
		return map;
	}*/
	
	@ResponseBody
	@GetMapping("/checkUid")
	public Map<String, String> checkUid(@RequestHeader Map<String, String> map) {
		log.info(map.toString());
		log.info(map.get("uid"));
		
		Integer result = service.countByUid(map.get("uid"));
		map.put("result", result.toString());
		return map;
	}
	
	@ResponseBody
	@PostMapping("/checkEmail")
	public Map<String, Object> checkEmail(@RequestBody Map<String, Object> map) {
		
		// 중복 메일 체크
		int result = service.countByEmail((String)map.get("email"));
		log.info((String)map.get("email"));
		
		// 결과 저장
		map.put("result", result);
		
		// 결과가 0이면 이메일 전송
		if(result == 0) emailService.send(map);
		
		return map;
	}
	
}
