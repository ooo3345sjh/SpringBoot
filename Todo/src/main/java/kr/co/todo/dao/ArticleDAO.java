package kr.co.todo.dao;

import kr.co.todo.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDAO {
    List<ArticleVO> select();
    int insert(ArticleVO vo);
    int delete(@Param(value = "no") int no);
    int update(@Param(value = "no") int no, @Param(value = "status") String status);
}
