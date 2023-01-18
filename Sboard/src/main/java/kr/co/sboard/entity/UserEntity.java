package kr.co.sboard.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "board_user")
public class UserEntity {
	
	@Id
	private String uid;
	private String pass;
	private String name;
	private String nick;
	private String email;
	private String hp;
	private Integer grade;
	private String zip;
	private String addr1;
	private String addr2;
	private String regip;
	private String rdate;
	private String wdate;
	private String terms;
	private String privacy;
}