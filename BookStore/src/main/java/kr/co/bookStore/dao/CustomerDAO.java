package kr.co.bookStore.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bookStore.vo.CustomerVO;


@Repository
public class CustomerDAO {
	
	@Autowired
	SqlSessionTemplate mybatis;
	
	public CustomerVO selectCustomer(String custId) {
		return mybatis.selectOne("customer.selectCustomer", custId);
	}
	
	public List<CustomerVO> selectCustomers() {
		return mybatis.selectList("customer.selectCustomers");
	}
	
	public void insertCustomer(CustomerVO vo) {
		mybatis.insert("customer.insertCustomer", vo);
	}
	
	public void updateCustomer(CustomerVO vo) {
		mybatis.update("customer.updateCustomer", vo);
	}
	
	public void deleteCustomer(String custId) {
		mybatis.delete("customer.deleteCustomer", custId);
	}
	

}
