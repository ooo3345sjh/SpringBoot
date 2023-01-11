package kr.co.ch07.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
@Data
@Entity
@Table(name = "user1")
public class User2VO {
	
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY) Auto_increment 컬럼일시에 명시할 것
	private String uid;
	private String name;
	private String hp;
	private int age;
}
