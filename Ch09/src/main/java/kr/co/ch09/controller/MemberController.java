package kr.co.ch09.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ch09.service.MemberService;
import kr.co.ch09.vo.MemberVO;

@RestController
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@GetMapping("/member")
	public List<MemberVO> list1() {
		List<MemberVO> Members = service.selectMemebers();
		return Members;
	}
	
	@GetMapping("/member/{id}")
	public MemberVO list2(@PathVariable("id") String uid) {
		return service.selectMemeber(uid);
	}
	
	@PostMapping("/member")
	public Map<String, Integer> register(MemberVO vo) {
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", 1);
		
		service.insertMemeber(vo);
		return resultMap;
	}
	
	@PutMapping("/member")
	public Map<String, Integer> modify(MemberVO vo) {
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", 1);
		
		service.updateMember(vo);
		return resultMap;
	}

	@DeleteMapping("/member/{id}")
	public Map<String, Integer> delete(@PathVariable("id") String uid) {
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", 1);
		
		service.deleteMember(uid);
		return resultMap;
	}

}
