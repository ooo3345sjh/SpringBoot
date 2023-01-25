package kr.co.sboard.vo;

import javax.validation.constraints.Size;

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
	
	@Size(max = 5, message = "{required.user.zip}")
	private String zip;
	
	@Size(max = 100, message = "{required.user.addr}")
	private String addr;

	@Size(max = 100, message = "{required.user.addrDetail}")
	private String addrDetail;
	
	private String regip;
	private String rdate;
	private String wdate;
}
