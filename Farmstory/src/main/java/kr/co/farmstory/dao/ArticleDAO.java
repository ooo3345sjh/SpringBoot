package kr.co.farmstory.dao;

import java.util.List;

import kr.co.farmstory.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleDAO {
	public List<ArticleVO> selectAll(int limitStart);
	public ArticleVO select(int no);
	public int insert(ArticleVO vo);
	public int delete(int no);
	public int update(ArticleVO vo);
	public int countAll();
}
