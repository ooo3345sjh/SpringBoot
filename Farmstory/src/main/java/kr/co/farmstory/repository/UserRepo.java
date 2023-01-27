package kr.co.farmstory.repository;


import kr.co.farmstory.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<UserEntity, String> {
	
	public int countUserEntityByUid(String uid);
	public int countUserEntityByEmail(String email);
	public int countUserEntityByNick(String nick);
	public int countUserEntityByHp(String hp);

}
