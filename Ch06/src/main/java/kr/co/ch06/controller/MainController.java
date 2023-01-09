package kr.co.ch06.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.ch06.vo.User1VO;

@Controller
public class MainController {
	
	
	@GetMapping(value = {"/", "/index"})
	public String index(Model model) {
		
		String tit1 = "스프링부트";
		String tit2 = "타임리프";
		
		User1VO user1 = new User1VO();
		user1.setUid("a101");
		user1.setName("김유신");
		user1.setHp("010-1234-2324");
		user1.setAge(30);
		
		User1VO user2 = null;
		
		List<User1VO> users = new ArrayList<>();
		users.add(new User1VO("a102", "아무개", "010-4444-9981", 32));
		users.add(new User1VO("a103", "강호동", "010-5555-9981", 51));
		users.add(new User1VO("a104", "유재석", "010-3333-9981", 31));
		users.add(new User1VO("a105", "김길수", "010-2222-9981", 11));
		users.add(new User1VO("a106", "강감찬", "010-1111-9981", 41));
		
		model.addAttribute("tit1", tit1);
		model.addAttribute("tit2", tit2);
		model.addAttribute("user1", user1);
		model.addAttribute("user2", user2);
		model.addAttribute("users", users);
		
		return "/index";
	}
	
	@GetMapping("/hello")
	public void hello() {}
	
	@GetMapping("/welcome")
	public void welcome() {}
	
	@GetMapping("/greeting")
	public void greeting() {}
	
}
