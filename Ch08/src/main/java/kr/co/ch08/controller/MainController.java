package kr.co.ch08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value = {"/", "/index"})
	public String index() {
		return "/index";
	}
	
	@GetMapping("admin/success")
	public void adminSuccess() {}
	
	@GetMapping("member/success")
	public void memberSuccess() {}
	
	@GetMapping("guest/success")
	public void guestSuccess() {}

}
