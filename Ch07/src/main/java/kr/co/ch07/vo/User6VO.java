package kr.co.ch07.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user6")
public class User6VO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seq;
	private String name;
	private int gender;
	private int age;
	private String addr;
}
