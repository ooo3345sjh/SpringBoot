package kr.co.vboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import kr.co.vboard.dao.UserDAO;
import kr.co.vboard.repository.UserRepo;
import kr.co.vboard.vo.TermsVO;
import kr.co.vboard.vo.UserVO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private UserRepo userRepo;
	
	public TermsVO findTerms() {return userDao.selectTerms();};
	public int countUid (String uid) {
		return userRepo.countByUid(uid);
	}
	public UserVO findUser() {return null;};
	public List<UserVO> findUsers() {return null;};
	public int modifyUser() {return 0;};
	public int removeUser() {return 0;};
	public int saveUser() {return 0;};

}
