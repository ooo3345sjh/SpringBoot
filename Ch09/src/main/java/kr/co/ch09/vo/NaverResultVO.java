package kr.co.ch09.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NaverResultVO {
	
	private String lastBuildDate;
	private int total;
	private int start;
	private int display;
	private List<BookVO> items;

}
