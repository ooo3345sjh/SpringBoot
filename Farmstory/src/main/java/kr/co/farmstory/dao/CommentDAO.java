package kr.co.farmstory.dao;

import kr.co.farmstory.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDAO {

    int insert(CommentVO vo) throws Exception;
    int update(CommentVO vo) throws Exception;
    int delete(@Param("no") Integer no, @Param("uid") String uid) throws Exception;
    List<CommentVO> selectAll() throws Exception;
    CommentVO select() throws Exception;
    int countAll(Integer parent) throws Exception;

}
