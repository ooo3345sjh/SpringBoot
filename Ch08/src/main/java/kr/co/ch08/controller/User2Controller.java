package kr.co.ch08.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user2")
public class User2Controller {
	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {}

}
