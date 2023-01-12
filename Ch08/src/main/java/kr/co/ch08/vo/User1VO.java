package kr.co.ch08.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user1")
public class User1VO {
	
	@Id
	private String uid;
	
	private String pass;
	private String name;
	private String hp;
	private int age;

}
