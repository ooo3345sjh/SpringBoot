package kr.co.sboard.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sboard.service.ArticleService;
import kr.co.sboard.service.FileService;
import kr.co.sboard.vo.ArticleVO;
import kr.co.sboard.vo.FileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService service;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("/list")
	public void list(Model model, int page) { 
		service.getArticles(model, page); 
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@GetMapping("/view")
	public void view(Model model, int no) {
		model.addAttribute("article", service.getArticle(no));
	}
	
	@GetMapping("/write")
	public void write() {}
	
	@PostMapping("/write")
	public String write(String page, ArticleVO vo) {
		service.write(vo);
		return "redirect:list?page=" + page;
	}
	
	@GetMapping("/download")
	public ResponseEntity<Resource> download(int fno) throws IOException {
		FileVO vo = fileService.getFile(fno);
		log.info(vo.toString());
		return service.fileDownload(vo);
	}
	
	@GetMapping("/auth")
	@ResponseBody
	public Object auth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	/*@ExceptionHandler(Exception.class)
	public Map<String, Object> ExceptionHandler(Exception e) {
		Map<String, Object> map = new HashMap<>();
		map.put("result : ", -9999);
		
		return map;
	}*/

}
