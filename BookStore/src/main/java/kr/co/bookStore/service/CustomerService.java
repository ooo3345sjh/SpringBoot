package kr.co.bookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookStore.dao.BookDAO;
import kr.co.bookStore.dao.CustomerDAO;
import kr.co.bookStore.vo.BookVO;
import kr.co.bookStore.vo.CustomerVO;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDAO dao;

	public CustomerVO selectCustomer(String custId) {
		return dao.selectCustomer(custId);
	}
	
	public List<CustomerVO> selectCustomers() {
		return dao.selectCustomers();
	}
	
	public void insertCustomer(CustomerVO vo) {
		dao.insertCustomer(vo);
	}
	
	public void updateCustomer(CustomerVO vo) {
		dao.updateCustomer(vo);
	}
	
	public void deleteCustomer(String custId) {
		dao.deleteCustomer(custId);
	}
}
