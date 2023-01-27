package kr.co.farmstory.dao;

import java.util.List;

import kr.co.farmstory.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


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
