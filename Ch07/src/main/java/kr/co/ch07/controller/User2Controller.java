package kr.co.ch07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ch07.service.User2Service;
import kr.co.ch07.vo.User2VO;


@Controller
@RequestMapping("/user2")
public class User2Controller {

	@Autowired
	private User2Service service;
	
	@GetMapping("/list")
	public void list(Model model) {
		System.out.println(service.selectUser2s());
		model.addAttribute("users", service.selectUser2s()); 
	}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(User2VO vo) {
		service.insertUser2(vo);
		return "redirect:/user2/list";
	}
	
	@GetMapping("/modify")
	public void modify(Model model, String uid) {
		model.addAttribute("user", service.selectUser2(uid));
	}

	@PostMapping("/modify")
	public String modify(User2VO vo) {
		service.updateUser2(vo);
		return "redirect:/user2/list";
	}
	
	@GetMapping("/delete")
	public String delete(String uid) {
		service.deleteUser2(uid);
		return "redirect:/user2/list";
	}
	
	
}