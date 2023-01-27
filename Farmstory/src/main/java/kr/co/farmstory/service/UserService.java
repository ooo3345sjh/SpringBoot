package kr.co.farmstory.service;

import kr.co.farmstory.dao.UserDAO;
import kr.co.farmstory.repository.UserRepo;
import kr.co.farmstory.vo.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
	
	private UserDAO dao;
	private UserRepo repo;
	private PasswordEncoder PasswordEncoder;
	
	public UserVO getUser(String uid) {
		return dao.select(uid);
	};
	
	public List<UserVO> getUserAll(){
		return dao.selectAll();
	};
	
	public int userDelete(String uid) {
		return dao.delete(uid);
	};
	
	public int userInsert(UserVO vo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails details = (WebAuthenticationDetails)auth.getDetails();
		
		vo.setPass(PasswordEncoder.encode(vo.getPass()));
		vo.setRegip(details.getRemoteAddress());
		return dao.insert(vo);
	};
	
	public int userUpdate(UserVO vo) {
		return dao.modify(vo);
	};
	
	public int countByUid(String uid) {
		return repo.countUserEntityByUid(uid);
	}
	
	public int countByEmail(String email) {
		return repo.countUserEntityByEmail(email);
	}
	
	public int countByNick(String nick) {
		return repo.countUserEntityByNick(nick);
	}
	
	public int countByHp(String hp) {
		return repo.countUserEntityByHp(hp);
	}

	public List<UserVO> checkDuplicate(UserVO user) {
		return dao.checkDuplicate(user);
	}
}
