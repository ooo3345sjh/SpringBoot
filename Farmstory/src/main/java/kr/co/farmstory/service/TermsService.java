package kr.co.farmstory.service;

import kr.co.farmstory.entity.TermsEntity;
import kr.co.farmstory.repository.TermsRepo;
import org.springframework.stereotype.Service;

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
