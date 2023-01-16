package kr.co.ch09.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ch09.repository.MemberRepositiory;
import kr.co.ch09.vo.MemberVO;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepositiory repo;
	
	public void insertMemeber(MemberVO vo) {
		repo.save(vo);
	};
	public MemberVO selectMemeber(String uid) {
		MemberVO vo = repo.findById(uid).get();
		System.out.println(vo);
		
		return vo;
	};
	public List<MemberVO> selectMemebers(){
		return repo.findAll();
	};
	
	public void updateMember(MemberVO vo) {
		repo.save(vo);
	};
	
	public void deleteMember(String uid) {
		repo.deleteById(uid);
	};
}
