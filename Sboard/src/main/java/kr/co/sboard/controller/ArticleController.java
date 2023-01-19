package kr.co.sboard.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.sboard.entity.UserEntity;

@Controller
public class ArticleController {
	
	@GetMapping("/list")
	public void list(@AuthenticationPrincipal UserEntity user, Model model) {
		String nick = user.getNick();
		model.addAttribute("user", nick);
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@GetMapping("/view")
	public void view() {}
	
	@GetMapping("/write")
	public void write() {}
	

}
