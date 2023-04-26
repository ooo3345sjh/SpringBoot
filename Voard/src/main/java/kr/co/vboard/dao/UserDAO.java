package kr.co.vboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.vboard.vo.TermsVO;
import kr.co.vboard.vo.UserVO;

@Repository
@Mapper
public interface UserDAO {

	UserVO selectUser();
	List<UserVO> selectUsers();
	int deleteUser();
	int updateUser();
	TermsVO selectTerms ();
}
