package kr.co.ch10.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User1VO {
	private String uid;
	private String pass;
	private String name;
	private String hp;
	private int age;
}
