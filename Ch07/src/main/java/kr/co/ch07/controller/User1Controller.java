package kr.co.ch07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ch07.service.User1Service;
import kr.co.ch07.vo.User1VO;


@Controller
public class User1Controller {

	@Autowired
	private User1Service service;
	
	@GetMapping("/user1/list")
	public String list(Model model) {
		System.out.println(service.selectUser1s());
		model.addAttribute("users", service.selectUser1s()); 
		return "/user1/list";
	}
	
	@GetMapping("/user1/register")
	public String register() {
		return "/user1/register";
	}
	
	@PostMapping("/user1/register")
	public String register(User1VO vo) {
		service.insertUser1(vo);
		return "redirect:/user1/list";
	}
	
	@GetMapping("/user1/modify")
	public void modify(Model model, String uid) {
		model.addAttribute("user", service.selectUser1(uid));
	}

	@PostMapping("/user1/modify")
	public String modify(User1VO vo) {
		service.updateUser1(vo);
		return "redirect:/user1/list";
	}
	
	@GetMapping("/user1/delete")
	public String delete(String uid) {
		service.deleteUser1(uid);
		return "redirect:/user1/list";
	}
	
	
}