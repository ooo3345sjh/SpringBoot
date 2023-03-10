package kr.co.ch08.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.ch08.repository.User2Repo;
//import kr.co.ch08.security.MyUserDetails.MyUserDetailsBuilder;
import kr.co.ch08.vo.User2VO;

@Service
public class SecurityUserService implements UserDetailsService {

	@Autowired
	private User2Repo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 스프링 시큐리티는 인증 동작방식은 아이디/패스워드를 한번에 조회하는 방식잉 아닌
		// 아이디만 이용해서 사용자 정보를 로딩하고 나중에 패스워드를 검증하는 방식
		User2VO user = repo.findById(username).get();
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		
		// Security 기본 사용자 객체 생성
		UserDetails userDts = User.builder()
								.username(user.getUid())
								.password(user.getPass())
								.roles("" + user.getGrade())
								.build();
		
		
		// Setter 초기화
		MyUserDetails myUser = new MyUserDetails();
		myUser.setUid(user.getUid());
		myUser.setPass(user.getPass());
		myUser.setName(user.getName());
		myUser.setHp(user.getHp());
		myUser.setAge(user.getAge());
		myUser.setRdate(user.getRdate());
		
		
		// 빌드 초기화 
		UserDetails myUser2 = MyUserDetails.builder()
									.uid(user.getUid())
									.pass(user.getPass())
									.name(user.getName())
									.hp(user.getHp())
									.grade(user.getGrade())
									.age(user.getAge())
									.rdate(user.getRdate())
									.build();
		
		System.out.println("loadUserByUsername...실행");
		
		return myUser2;
	}

}
