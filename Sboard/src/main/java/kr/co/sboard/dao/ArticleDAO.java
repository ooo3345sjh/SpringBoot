package kr.co.sboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.sboard.vo.ArticleVO;

@Mapper
@Repository
public interface ArticleDAO {

	public List<ArticleVO> selectAll(int beginPage);
	public ArticleVO select(int no);
	public int insert(ArticleVO vo);
	public int delete(int no);
	public int update(ArticleVO vo);
	public int countAll();
}
