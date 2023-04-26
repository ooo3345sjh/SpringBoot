package kr.co.vboard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
	
	private String uid;
	private String pass;
	private String name;
	private String nick;
	private String email;
	private String hp;
	private Integer grade;
	private String zip;
	private String addr;
	private String addrDetail;
	private String regip;
	private String rdate;
	private String wdate;
}
