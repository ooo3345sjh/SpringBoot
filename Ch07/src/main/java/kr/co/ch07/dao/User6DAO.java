package kr.co.ch07.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ch07.vo.User6VO;

@Mapper
@Repository
public interface User6DAO {
	public void insertUser6(User6VO vo);
	public User6VO selectUser6(int seq);
	public List<User6VO> selectUser6s();
	public void updateUser6(User6VO vo);
	public void deleteUser6(int seq);
}
