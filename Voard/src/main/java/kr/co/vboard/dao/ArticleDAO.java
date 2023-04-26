package kr.co.vboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.vboard.vo.ArticleVO;
import kr.co.vboard.vo.TermsVO;
import kr.co.vboard.vo.UserVO;

@Repository
@Mapper
public interface ArticleDAO {

	int insert(ArticleVO article);
	ArticleVO select(String no);
	List<ArticleVO> selectAll();
	int update(ArticleVO article);
	int delete(String uid, String no);
}
