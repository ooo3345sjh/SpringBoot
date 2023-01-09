package kr.co.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.user.service.User1Service;
import kr.co.user.vo.User1VO;

@Controller
@RequestMapping("/user1")
public class User1Controller {
	
	@Autowired
	private User1Service service;

	@GetMapping("/list")
	public void list(Model model) {
		model.addAttribute("users", service.selectUser1s());
	}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public void register(User1VO vo) {
		service.insertUser1(vo);
	}
	
	@GetMapping("/modify")
	public void modify(Model model, String uid) {
		model.addAttribute("user", service.selectUser1(uid));
	}
	
	@PostMapping("/modify")
	public String modify(User1VO vo) {
		service.updateUser1(vo);
		return "redirect:/user1/list";
	}
	
	@GetMapping("/delete")
	public String delete(String uid) {
		service.deleteUser1(uid);
		return "redirect:/user1/list";
	}
}
