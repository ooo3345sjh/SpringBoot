package kr.co.farmstory.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleVO {
	private Integer no;
	private Integer parent;
	private Integer comment;
	private String cate;
	private String title;
	private String content;
	private Integer file;
	private MultipartFile fname;
	private Integer hit;
	private String uid;
	private String regip;
	private Date rdate;
	
	// 추가 필드
	private String nick;
	private FileVO fileVO;
	private List<ArticleVO> cateList;

}
