package kr.co.sboard.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.sboard.entity.UserEntity;
import kr.co.sboard.security.MyUserDetails;

@Controller
public class ArticleController {
	
	@GetMapping("/list")
	public void list(@AuthenticationPrincipal MyUserDetails myUser, Model model) {
		UserEntity user = myUser.getUser();
		model.addAttribute("user", user);
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@GetMapping("/view")
	public void view() {}
	
	@GetMapping("/write")
	public void write() {}
	

}
