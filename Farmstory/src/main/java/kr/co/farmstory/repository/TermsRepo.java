package kr.co.farmstory.repository;

import kr.co.farmstory.entity.TermsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepo extends JpaRepository<TermsEntity, String> {
	TermsEntity findTermsVOByTno(Integer tno);
}
