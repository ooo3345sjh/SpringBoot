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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.vboard.entity.UserEntity;
import kr.co.vboard.jwt.JwtUtil;
import kr.co.vboard.repository.UserRepo;
import kr.co.vboard.security.SecurityUserService;
import kr.co.vboard.service.ArticleService;
import kr.co.vboard.service.UserService;
import kr.co.vboard.vo.ArticleVO;
import kr.co.vboard.vo.TermsVO;
import kr.co.vboard.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", allowedHeaders = "*") // 개발시에만 필요
@AllArgsConstructor
public class ArticleController {
	
	private ArticleService articleService;
	
	@PostMapping("/article")
	public int write(@ModelAttribute ArticleVO article, HttpServletRequest req) {
		log.debug(article.toString());
		article.setRegip(req.getRemoteAddr());
		
		return articleService.rSave(article);
	}
	
	@GetMapping("/articles")
	public Map list(@RequestParam(defaultValue = "1")int page) {
		Map map = new HashMap<>();
		articleService.getArticles(map, page); 
		return map;
	}
}
