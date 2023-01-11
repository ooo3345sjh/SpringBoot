package kr.co.ch07.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.ch07.vo.User6VO;
import kr.co.ch07.vo.User6VO;

@Repository // 생략가능 상속받기 때문에
public interface User6Repo extends JpaRepository<User6VO, Integer>{

	// JPA 쿼리메서드
	public User6VO findUser6VOByseq(String seq);
	public List<User6VO> findUser6VOByName(String name);
	public List<User6VO> findUser6VOByNameNot(String name);
	
	public User6VO findUser6VOByseqAndName(String seq, String name);
	public List<User6VO> findUser6VOByseqOrName(String seq, String name);
	
	public List<User6VO> findUser6VOByAgeGreaterThan(int age);
	public List<User6VO> findUser6VOByAgeGreaterThanEqual(int age);
	public List<User6VO> findUser6VOByAgeLessThan(int age);
	public List<User6VO> findUser6VOByAgeLessThanEqual(int age);
	
	public List<User6VO> findUser6VOByNameLike(String name); // name 앞 또는 뒤에 % 명시
	public List<User6VO> findUser6VOByNameContains(String name);
	public List<User6VO> findUser6VOByNameStartsWith(String name);
	public List<User6VO> findUser6VOByNameEndsWith(String name);
	
	public List<User6VO> findUser6VOByOrderByName();
	public List<User6VO> findUser6VOByOrderByAgeAsc();
	public List<User6VO> findUser6VOByOrderByAgeDesc();
	public List<User6VO> findUser6VOByAgeGreaterThanOrderByAgeDesc(int age);
	
	public int countUser6VOByseq(String seq);
	public int countUser6VOByName(String name);
	
	// JPQL
	@Query("SELECT u1 FROM User6VO as u1 WHERE age < 30")
	public List<User6VO> selectUserUnderAge30();
	
	@Query("SELECT u1 from User6VO as u1 WHERE u1.name = ?1")
	public List<User6VO> selectUserByName(String name);
	
	@Query("SELECT u1 from User6VO as u1 WHERE u1.name = :name")
	public List<User6VO> selectUserByNameWithParam(@Param("name") String name);
	
}
