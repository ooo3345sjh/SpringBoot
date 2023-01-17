package kr.co.ch10.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookVO {
	
	private String title;
	private String link;
	private String image;
	private String author;
	private String discount;
	private String publisher;
	private String pubdate;
	private String isbn;
	private String description;

}
