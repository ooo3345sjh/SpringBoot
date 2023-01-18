package kr.co.ch09.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.co.ch09.dao.User1DAO;
import kr.co.ch09.vo.User1VO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class User1Service {
	
	@Autowired
	private User1DAO dao;
	
	@Transactional(rollbackFor = Exception.class)
	public int insertUser1(User1VO vo) throws Exception {
		int result = 0;
		result += dao.insertUser1(vo);
		result += dao.insertUser1(vo);
		return result;
	};
	
	public User1VO selectUser1(String uid) throws Exception {
		return dao.selectUser1(uid);
	};
	public List<User1VO> selectUser1s() throws Exception {
		return dao.selectUser1s();
	};
	
	public int updateUser1(User1VO vo) throws Exception {
		return dao.updateUser1(vo);
	};
	
	public int deleteUser1(String uid) throws Exception {
		return dao.deleteUser1(uid);
	};
}
