package kr.co.sboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sboard.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String> {
	
	public int countUserEntityByUid(String uid);
	public int countUserEntityByEmail(String email);

}