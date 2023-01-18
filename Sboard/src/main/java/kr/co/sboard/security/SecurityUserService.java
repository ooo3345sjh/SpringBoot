package kr.co.sboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.sboard.entity.UserEntity;
import kr.co.sboard.repository.UserRepo;

@Service
public class SecurityUserService implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 스프링 시큐리티는 인증 동작방식은 아이디/패스워드를 한번에 조회하는 방식잉 아닌
		// 아이디만 이용해서 사용자 정보를 로딩하고 나중에 패스워드를 검증하는 방식
		UserEntity user = repo.findById(username).get();
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
			
		// 빌드 초기화 
		UserDetails myUser = MyUserDetails.builder()
									.user(user)
									.build();
		
		System.out.println("loadUserByUsername...실행");
		
		return myUser;
	}

}
