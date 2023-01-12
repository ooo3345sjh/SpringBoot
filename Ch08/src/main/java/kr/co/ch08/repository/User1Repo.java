package kr.co.ch08.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ch08.vo.User1VO;

public interface User1Repo extends JpaRepository<User1VO, String>{
	
	public User1VO findUser1VOByUidAndPass(String uid, String pass);

}
