package kr.co.ch09.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.co.ch09.dao.User1DAO;
import kr.co.ch09.vo.User1VO;

@Service
public class User1Service {
	
	@Autowired
	private User1DAO dao;
	
	@Transactional(rollbackFor = Exception.class)
	public int insertUser1(User1VO vo) {
		int result = 0;
		
		try {
			result = dao.insertUser1(vo);
			result = dao.insertUser1(vo);
		} catch (Exception e) {
			return result;
		}
		return result;
	};
	public User1VO selectUser1(String uid) {
		return dao.selectUser1(uid);
	};
	public List<User1VO> selectUser1s(){
		return dao.selectUser1s();
	};
	
	public int updateUser1(User1VO vo) {
		return dao.updateUser1(vo);
	};
	
	public int deleteUser1(String uid) {
		return dao.deleteUser1(uid);
	};
}
