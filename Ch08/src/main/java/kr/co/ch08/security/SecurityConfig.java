package kr.co.ch08.security;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@EnableWebSecurit
@Configuration
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
	@Autowired
	private SecurityUserService service;
	
	@Bean
	public SecurityFilterChain fillterChain(HttpSecurity http) throws Exception {
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll(); // 모든 자원에 대해서 모든 사용자 접근 허용
		http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN"); // admin 하위 모든 링크에 대해서 ADMIN에게만 접근 허용 
		http.authorizeHttpRequests().antMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER"); // member 하위 모든 링크에 대해서 ADMIN, MEMBER에게만 접근 허용
		http.rememberMe()
			.key("autoUser")
			.rememberMeParameter("autoUid")
			.tokenValiditySeconds(60)
			.userDetailsService(service);
			
		// 사이트 위변조 요청 방지
		// http.csrf().disable();
		
		// 로그인 설정
		http.formLogin()
		.loginPage("/user2/login")
		.defaultSuccessUrl("/user2/loginSuccess")
		.failureUrl("/user2/login?success=100)")
		.usernameParameter("uid")
		.passwordParameter("pass");
		
		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
		.logoutSuccessUrl("/user2/login?success=200");
		
		return http.build();
	}
	
	@Bean
	public UserDetailsManager users(DataSource dataSource) {

	    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
	    users.createUser(user);
	    return users;
	}
/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll(); // 모든 자원에 대해서 모든 사용자 접근 허용
		http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN"); // admin 하위 모든 링크에 대해서 ADMIN에게만 접근 허용 
		http.authorizeHttpRequests().antMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER"); // member 하위 모든 링크에 대해서 ADMIN, MEMBER에게만 접근 허용
		http.rememberMe()
			.key("autoUser")
			.rememberMeParameter("autoUid")
			.tokenValiditySeconds(60)
			.userDetailsService(service);
			
		// 사이트 위변조 요청 방지
		// http.csrf().disable();
		
		// 로그인 설정
		http.formLogin()
		.loginPage("/user2/login")
		.defaultSuccessUrl("/user2/loginSuccess")
		.failureUrl("/user2/login?success=100)")
		.usernameParameter("uid")
		.passwordParameter("pass");
		
		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
		.logoutSuccessUrl("/user2/login?success=200");
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// 로그인 인증처리 서비스 등록
		auth.userDetailsService(service).passwordEncoder(new MessageDigestPasswordEncoder("SHA-256"));
		
	}
 */
	
	
}
