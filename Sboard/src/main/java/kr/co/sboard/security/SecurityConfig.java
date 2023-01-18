package kr.co.sboard.security;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	@Autowired
	private SecurityUserService service;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll(); // 모든 자원에 대해서 모든 사용자 접근 허용
		http.authorizeHttpRequests().antMatchers("/list").hasAnyRole("2", "3", "3", "4", "5"); 
		
		// 자동 로그인 설정
//		http.rememberMe()
//			.key("autoUser")
//			.rememberMeParameter("autoUid")
//			.tokenValiditySeconds(600)
//			.userDetailsService(service);
			
		// 사이트 위변조 요청 방지
		http.csrf().disable();
		
		// 로그인 설정
		http.formLogin()
		.loginPage("/user/login")
		.defaultSuccessUrl("/list", false)
		.failureUrl("/user/login?success=100")
		.passwordParameter("pass")
		.usernameParameter("uid");
		
		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/user/login?success=200");
		
		return http.build();
	}
	
	@Bean
    public PasswordEncoder  PasswordEncoder () {
		return new BCryptPasswordEncoder(16);
    }
	
//	
//	@Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//       return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
//	}
	
}
