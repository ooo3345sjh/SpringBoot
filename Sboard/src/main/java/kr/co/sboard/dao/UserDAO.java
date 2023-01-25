package kr.co.sboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.sboard.vo.UserVO;

@Mapper
@Repository
public interface UserDAO {
	
	public UserVO selectTerms();
	public UserVO select(String uid);
	public List<UserVO> selectAll();
	public int delete(String uid);
	public int insert(UserVO vo);
	public int modify(UserVO vo);
	public int countByEmail(String email);
	public List<UserVO> checkDuplicate(UserVO vo);
}
