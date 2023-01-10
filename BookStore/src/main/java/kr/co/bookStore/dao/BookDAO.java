package kr.co.bookStore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bookStore.vo.BookVO;

@Mapper
@Repository
public interface BookDAO {
	
	public BookVO selectBook(String bookId);
	
	public List<BookVO> selectBooks();
	
	public void insertBook(BookVO vo);
	
	public void updateBook(BookVO vo);
	
	public void deleteBook(String bookId);
	
}
