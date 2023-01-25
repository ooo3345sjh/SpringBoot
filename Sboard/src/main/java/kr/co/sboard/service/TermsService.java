package kr.co.sboard.service;

import org.springframework.stereotype.Service;

import kr.co.sboard.entity.TermsEntity;
import kr.co.sboard.repository.TermsRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class TermsService {
	
	private TermsRepo repo;
	
	public TermsEntity getTerms() {
		log.info("getTerms...");
		return repo.findTermsVOByTno(1);
	}
	
}
