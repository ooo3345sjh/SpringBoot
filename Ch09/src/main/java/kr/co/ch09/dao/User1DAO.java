package kr.co.ch09.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ch09.vo.User1VO;

@Mapper
@Repository
public interface User1DAO {
	
	public int insertUser1(User1VO vo) throws Exception;
	public User1VO selectUser1(String uid) throws Exception;
	public List<User1VO> selectUser1s() throws Exception;
	public int updateUser1(User1VO vo) throws Exception;
	public int deleteUser1(String uid) throws Exception;
	
}
