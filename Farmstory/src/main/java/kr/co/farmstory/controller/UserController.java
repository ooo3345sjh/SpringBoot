package kr.co.farmstory.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.co.farmstory.service.EmailService;
import kr.co.farmstory.service.TermsService;
import kr.co.farmstory.service.UserService;
import kr.co.farmstory.validator.UserVaildator;
import kr.co.farmstory.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

	private UserService userService;
	private TermsService termsService;
	private EmailService emailService;

//  직접 커스텀해서 Validator등록 가능	
	@InitBinder("userVO") // UserVO 객체만 해당
    public void setBind(WebDataBinder binder) {
        binder.addValidators(new UserVaildator(userService));
    }
	
	// login
	@GetMapping("/login")
	public void login(HttpServletRequest req) {
		log.info("login Get...");
		// 이전 페이지로 되돌아가기 위한 Referer 헤더값을 세션의 prevPage attribute로 저장
		String url = req.getHeader("Referer");
		if(url != null && !url.contains("/login")){
			req.getSession().setAttribute("prevPage", url);
		}
	}

	@GetMapping("/logout")
	public void logout(){}
	
	// register
	@GetMapping("/register")
	public void register(Model model) {
		model.addAttribute(new UserVO());
		model.addAttribute("error", false);
	}
	
	@PostMapping("/register")
	public String register(@Valid UserVO vo, BindingResult error) {
		log.info(error.toString());
		
		if(!error.hasErrors()) {
			
			int result = userService.userInsert(vo);
			
			if(result != 0) return "redirect:/user/login";
		}
		
		return "/user/register";
	}
	
	// terms
	@GetMapping("/terms")
	public void terms(Model model) {
		model.addAttribute("terms", termsService.getTerms());
	}
	
	// 그 외의 컨트롤러
	@ResponseBody
	@PostMapping("/checkUid")
	public Map<String, String> checkUid(@RequestBody Map<String, String> map) {
		Integer result = userService.countByUid(map.get("uid"));
		map.put("result", result.toString());
		return map;
	}
	
	@ResponseBody
	@PostMapping("/checkNick")
	public Map<String, String> checkNick(@RequestBody Map<String, String> map) {
		Integer result = userService.countByNick(map.get("nick"));
		map.put("result", result.toString());
		return map;
	}
	
	@ResponseBody
	@PostMapping("/checkHp")
	public Map<String, String> checkHp(@RequestBody Map<String, String> map) {
		Integer result = userService.countByHp(map.get("hp"));
		map.put("result", result.toString());
		return map;
	}
	
	@ResponseBody
	@PostMapping("/checkEmail")
	public Map<String, Object> checkEmail(@RequestBody Map<String, Object> map) {

		log.info("checkEmail Post...");
		
		// 중복 메일 체크
		int result = userService.countByEmail((String)map.get("email"));
		
		// 결과 저장
		map.put("result", result);
		map.put("status", 1);
		
		// 결과가 0이면 이메일 전송
		if(result == 0) emailService.send(map);
		
		return map;
	}
	
}

//	GET방식
//	@ResponseBody
//	@GetMapping("/checkUid")
//	public Map<String, Object> checkUid(@RequestHeader String uid) {
//		Map<String, Object> map = new HashMap<>();
//		map.put("result", service.countByUid(uid)); // 입력한 uid에 해당하는 회원 유무 확인(존재O : 1, 존재X : 0)반환
//		return map;
//	}