package kr.co.vboard.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.vboard.entity.UserEntity;
import kr.co.vboard.jwt.JwtUtil;
import kr.co.vboard.repository.UserRepo;
import kr.co.vboard.security.SecurityUserService;
import kr.co.vboard.service.UserService;
import kr.co.vboard.vo.TermsVO;
import kr.co.vboard.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
public class UserController {
	
	private AuthenticationManager authenticationManager;
	private SecurityUserService securityUserService;
	private JwtUtil jwtUtil;
	private UserService userService;
	private UserRepo userRepo;
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/user/terms")
	public TermsVO terms(){
		return userService.findTerms();
	}
	
	@GetMapping("/user/check-uid")
	public int checkUid(String uid){
		log.debug(uid);
		return userService.countUid(uid);
	}
	
	@PostMapping("/user/register")
	public void regUser(@RequestBody UserEntity user, HttpServletRequest req) {
		user.setRegip(req.getRemoteAddr());
		user.setPass(passwordEncoder.encode( user.getPass()));
		user.setRdate(new Date());
		userRepo.save(user);
	}
	
	@GetMapping("/user")
	public UserEntity user(@AuthenticationPrincipal UserEntity user) {
		return user;
	}
	
	@PostMapping("/user/login")
	public Map<String, Object> login(@RequestBody UserVO user) {
		log.debug(user.toString());

		log.debug("login...1");
		
		// Security 인증처리
		UserEntity userDetails = userRepo.findById(user.getUid())
										  .orElseThrow(()-> new UsernameNotFoundException(user.getUid()));
		log.debug("login...2");
		
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userDetails, user.getPass())
		);
		log.debug("login...3");
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		// JWT 생성
		String token = jwtUtil.generateToken(user.getUid());
		log.debug("login...4 : " + token);

		// JWT 출력
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("accessToken", token);
		resultMap.put("user", userDetails);
		return resultMap;
	}
	
	
	public void logout() {}
}
