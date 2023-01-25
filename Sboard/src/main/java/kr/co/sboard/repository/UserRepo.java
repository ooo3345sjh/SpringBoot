package kr.co.sboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.sboard.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String> {
	
	public int countUserEntityByUid(String uid);
	public int countUserEntityByEmail(String email);
	public int countUserEntityByNick(String nick);
	public int countUserEntityByHp(String hp);

}
