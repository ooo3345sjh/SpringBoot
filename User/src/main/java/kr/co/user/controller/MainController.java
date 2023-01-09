package kr.co.user.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class MainController {
	
	@GetMapping(value = {"/", "index"})
	public String index() {
		return "/index";
	}

}
