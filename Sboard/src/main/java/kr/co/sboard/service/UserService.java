package kr.co.sboard.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sboard.dao.UserDAO;
import kr.co.sboard.repository.UserRepo;
import kr.co.sboard.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder PasswordEncoder;
	
	public UserVO selectTerms() {
		return dao.selectTerms();
	}
	
	public UserVO getUser(String uid) {
		return dao.select(uid);
	};
	public List<UserVO> getUserAll(){
		return dao.selectAll();
	};
	public int userDelete(String uid) {
		return dao.delete(uid);
	};
	public int userInsert(UserVO vo, HttpServletRequest req) {
		vo.setPass(PasswordEncoder.encode(vo.getPass1()));
		vo.setRegip(req.getRemoteAddr());
		return dao.insert(vo);
	};
	
	public int userUpdate(UserVO vo) {
		return dao.modify(vo);
	};
	
	public int countByUid(String uid) {
		return repo.countUserEntityByUid(uid);
	}
	
	public int countByEmail(String email) {
//		return dao.countByEmail(email);
		return repo.countUserEntityByEmail(email);
	}
}
