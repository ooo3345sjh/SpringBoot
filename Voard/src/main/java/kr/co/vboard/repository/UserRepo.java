package kr.co.vboard.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.vboard.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String> {
	
	public int countByUid(String uid);

}
