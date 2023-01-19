package kr.co.sboard.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.sboard.entity.UserEntity;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService service;
	
	@GetMapping("/list")
	public void list(Model model, int page) {
		Map<String, Object> map = new HashMap<>();
		service.getArticles(page, map);
		model.addAttribute("map", map);
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@GetMapping("/view")
	public void view() {}
	
	@GetMapping("/write")
	public void write() {}
	
	@PostMapping("/write")
	public void write(ArticleVO vo) {
		service.write(vo);
	}
	

}
