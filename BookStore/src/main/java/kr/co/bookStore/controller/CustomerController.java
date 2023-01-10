package kr.co.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookStore.service.BookService;
import kr.co.bookStore.service.CustomerService;
import kr.co.bookStore.vo.BookVO;
import kr.co.bookStore.vo.CustomerVO;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@GetMapping("/list")
	public void list(Model model) {
		model.addAttribute("customers", service.selectCustomers());
	}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public void register(CustomerVO vo) {
		service.insertCustomer(vo);
	}
	
	@GetMapping("/modify")
	public void modify(Model model, String custId) {
		model.addAttribute("customer", service.selectCustomer(custId));
	}
	
	@PostMapping("modify")
	public String modify(CustomerVO vo) {
		service.updateCustomer(vo);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/delete")
	public String delete(String custId) {
		service.deleteCustomer(custId);
		return "redirect:/customer/list";
	}
}
