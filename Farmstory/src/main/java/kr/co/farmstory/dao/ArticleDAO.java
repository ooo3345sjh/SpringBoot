package kr.co.farmstory.dao;

import java.util.List;
import java.util.Map;

import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.SearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleDAO {
	List<ArticleVO> selectAll(SearchCondition sc);
	ArticleVO select(int no);
	int insert(ArticleVO vo);
	int delete(int no) throws  Exception;
	int update(ArticleVO vo);
	int countAll(SearchCondition sc);
	int updateCommentCnt(@Param(value = "count") Integer count, @Param(value = "no") Integer no);
	int updateHit(@Param(value = "no") Integer no) throws Exception;
	List<ArticleVO> selectCate() throws Exception;
	List<ArticleVO> selectCate2() throws Exception;
}
