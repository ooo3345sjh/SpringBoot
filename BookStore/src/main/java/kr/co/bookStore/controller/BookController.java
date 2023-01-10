package kr.co.bookStore.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookStore.service.BookService;
import kr.co.bookStore.vo.BookVO;

@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService service;
	
	@GetMapping("/list")
	public void list(Model model) {
		model.addAttribute("books", service.selectBooks());
	}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public void register(@Validated BookVO vo, Errors errors) {
		System.out.println(vo.getBookname());
		System.out.println(errors.hasErrors());
		System.out.println("book :" + vo);
		service.insertBook(vo);
		System.out.println("book :" + vo);
		
	}
	
	@GetMapping("/modify")
	public void modify(Model model, String bookId) {
		model.addAttribute("book", service.selectBook(bookId));
	}
	
	@PostMapping("modify")
	public String modify(BookVO vo) {
		service.updateBook(vo);
		return "redirect:/book/list";
	}
	
	@GetMapping("/delete")
	public String delete(String bookId) {
		service.deleteBook(bookId);
		return "redirect:/book/list";
	}
}
