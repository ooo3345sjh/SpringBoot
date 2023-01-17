package kr.co.ch10.vo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class MemberVO {
	
	@Id
	private String uid;
	
	private String name;
	private String hp;
	private String pos;
	private int dep;
	
	/*
	@CreationTimestamp
	//@Column(updatable = false)
	private LocalDateTime InsertRdate;
	*/
	private LocalDateTime rdate;
	
	@PrePersist // 데이터 생성이 이루어질때 사전 작업
	@PreUpdate  // 데이터 수정이 이루어질때 사전 작업
	public void prePersist() {
		this.rdate = LocalDateTime.now();
	}
	
}
