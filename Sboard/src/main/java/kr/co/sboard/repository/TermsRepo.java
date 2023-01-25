package kr.co.sboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sboard.entity.TermsEntity;

public interface TermsRepo extends JpaRepository<TermsEntity, String> {
	public TermsEntity findTermsVOByTno(Integer tno);
}
