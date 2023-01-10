package kr.co.bookStore.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import kr.co.bookStore.dao.BookDAO;
import kr.co.bookStore.vo.BookVO;

@Service
public class BookService {
	
	@Autowired
	private BookDAO dao;

	public BookVO selectBook(String bookId) {
		return dao.selectBook(bookId);
	}
	
	public List<BookVO> selectBooks() {
		return dao.selectBooks();
	}
	
	public void insertBook(BookVO vo) {
		dao.insertBook(vo);
	}
	
	public void updateBook(BookVO vo) {
		dao.updateBook(vo);
	}
	
	public void deleteBook(String bookId) {
		dao.deleteBook(bookId);
	}
}
