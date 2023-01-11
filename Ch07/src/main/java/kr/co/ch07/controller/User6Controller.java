package kr.co.ch07.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ch07.service.User6Service;
import kr.co.ch07.vo.User6VO;


@Controller
public class User6Controller {

	@Autowired
	private User6Service service;
	
	@GetMapping("/user6/list")
	public String list(Model model) {
		List<User6VO> users = service.selectUser6s();
		System.out.println(users);
		model.addAttribute("users", users); 
		return "/user6/list";
	}
	
	@GetMapping("/user6/register")
	public String register() {
		return "/user6/register";
	}
	
	@PostMapping("/user6/register")
	public String register(User6VO vo) {
		service.insertUser6(vo);
		return "redirect:/user6/list";
	}
	
	@GetMapping("/user6/modify")
	public void modify(Model model, int seq) {
		model.addAttribute("user", service.selectUser6(seq));
	}

	@PostMapping("/user6/modify")
	public String modify(User6VO vo) {
		service.updateUser6(vo);
		return "redirect:/user6/list";
	}
	
	@GetMapping("/user6/delete")
	public String delete(int seq) {
		service.deleteUser6(seq);
		return "redirect:/user6/list";
	}
	
	
}