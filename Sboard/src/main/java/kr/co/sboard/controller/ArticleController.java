package kr.co.sboard.controller;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sboard.service.ArticleService;
import kr.co.sboard.service.FileService;
import kr.co.sboard.vo.ArticleVO;
import kr.co.sboard.vo.FileVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@AllArgsConstructor
public class ArticleController {
	
	private ArticleService articleService;
	private FileService fileService;
	
	// List
	@GetMapping("/list")
	public void list(Model model, int page) { 
		articleService.getArticles(model, page); 
	}
	
	// view
	@GetMapping("/view")
	public void view(Model model, int no) {
		model.addAttribute("article", articleService.getArticle(no));
	}
	
	// write
	@GetMapping("/write")
	public void write() {}
	
	@PostMapping("/write")
	public String write(String page, ArticleVO vo) {
		articleService.write(vo);
		return "redirect:list?page=" + page;
	}
	
	// modify
	@GetMapping("/modify")
	public void modify(Model model, int no) {
		model.addAttribute(articleService.getArticle(no));
	}
	
	@PostMapping("/modify")
	public String modify(ArticleVO vo, String page) {
		articleService.modify(vo);
		return "redirect:view?page=" + page + "&no=" + vo.getNo();
	}
	
	// delete
	@GetMapping("/delete")
	public String delete(int no, String page) {
		articleService.delete(no);
		return "redirect:list?page=" + page;
	}
	
	// 그 외의 컨트롤러
	@GetMapping("/download")
	public ResponseEntity<Resource> download(int fno) throws IOException {
		return fileService.fileDownload(fno);
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
