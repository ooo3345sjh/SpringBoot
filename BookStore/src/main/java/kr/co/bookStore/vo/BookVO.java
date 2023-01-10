package kr.co.bookStore.vo;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
@Data
public class BookVO {
	private int bookId;
	
//	@NotBlank(message = "반드시 도서명을 입력해야합니다.")
	private String bookname;
	
	private String publisher;
	private int price;
}
