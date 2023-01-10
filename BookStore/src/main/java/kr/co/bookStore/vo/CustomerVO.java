package kr.co.bookStore.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
@Data
public class CustomerVO {
	private int custId;
	private String name;
	private String address;
	private String phone;
}


