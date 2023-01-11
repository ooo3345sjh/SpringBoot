package kr.co.ch07.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.ch07.vo.User2VO;

@Repository // 생략가능 상속받기 때문에
public interface User2Repo extends JpaRepository<User2VO, String>{

	// JPA 쿼리메서드
	public User2VO findUser2VOByUid(String uid);
	public List<User2VO> findUser2VOByName(String name);
	public List<User2VO> findUser2VOByNameNot(String name);
	
	public User2VO findUser2VOByUidAndName(String uid, String name);
	public List<User2VO> findUser2VOByUidOrName(String uid, String name);
	
	public List<User2VO> findUser2VOByAgeGreaterThan(int age);
	public List<User2VO> findUser2VOByAgeGreaterThanEqual(int age);
	public List<User2VO> findUser2VOByAgeLessThan(int age);
	public List<User2VO> findUser2VOByAgeLessThanEqual(int age);
	
	public List<User2VO> findUser2VOByNameLike(String name); // name 앞 또는 뒤에 % 명시
	public List<User2VO> findUser2VOByNameContains(String name);
	public List<User2VO> findUser2VOByNameStartsWith(String name);
	public List<User2VO> findUser2VOByNameEndsWith(String name);
	
	public List<User2VO> findUser2VOByOrderByName();
	public List<User2VO> findUser2VOByOrderByAgeAsc();
	public List<User2VO> findUser2VOByOrderByAgeDesc();
	public List<User2VO> findUser2VOByAgeGreaterThanOrderByAgeDesc(int age);
	
	public int countUser2VOByUid(String uid);
	public int countUser2VOByName(String name);
	
	// JPQL
	@Query("SELECT u1 FROM User2VO as u1 WHERE age < 30")
	public List<User2VO> selectUserUnderAge30();
	
	@Query("SELECT u1 from User2VO as u1 WHERE u1.name = ?1")
	public List<User2VO> selectUserByName(String name);
	
	@Query("SELECT u1 from User2VO as u1 WHERE u1.name = :name")
	public List<User2VO> selectUserByNameWithParam(@Param("name") String name);
	
}
