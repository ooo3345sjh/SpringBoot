# 8장 **Spring Security**

## 1. 인증과 인가

- 인증(Authentication)은 애플리케이션에서 특정 Resource(디렉터리, 파일 등)에 대해 특정 작업을 수행할 수 있는 주체인지 확인하는 과정
- 인가(Authorization)는 권한부여 또는 허가와 같은 의미로 인증 이후의 과정
- 일반적으로 인증을 통해 사용자를 식별하고 인가를 통해 시스템 자원에 대한 접근을 통제

![Untitled](https://user-images.githubusercontent.com/111489860/235748667-1222953a-267a-406f-8a2e-353180b86293.png)

## 2. Spring Security란?

- Spring Security는 인증과 인가 관련 기능 구현을 손쉽게 처리 해주는 라이브러리
- Spring Security의 인증을 처리 방식의 기본은 HttpSession 방식
- Spring Security는 서블릿 필터(Servlet Filter) 기반으로 동작하고, 다양한 기능들을 Filter로 제공

[]()

## 3. Spring Security 동작방식

- Spring Security는 Security Filter들의 상호작용에 의해 처리
- UsernamePasswordAuthenticationFilter는 사용자가 입력한 정보를 이용해 인증을 처리
- FilterSecurityInterceptor는 인증에 성공한 사용자가 해당 리소스에 접근할 권한이 있는지를 검증

![Untitled](https://user-images.githubusercontent.com/111489860/235748731-5cdd4672-ce27-4b04-bc84-5136220c0a68.png)

## 4. Spring Security 실습

### 1. 라이브러리 추가

- [https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)
- [https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test)

### 2. Controller

```java
package kr.co.ch08.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ch08.service.User2Service;
import kr.co.ch08.vo.User2VO;

@Controller
@RequestMapping("/user2")
public class User2Controller {
	
	@Autowired
	private User2Service service;
	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess(Principal principal, Model model) {

		// 사용자 정보(아이디, 패스워드, 이름...) 가져오는 방법2
		// 이 방법을 사용하면 UserDetails를 커스텀할 필요가 없음
		// 단, DB에 한번 더 접속하게 됨.
		if(principal != null) {
			User2VO vo = service.selectUser2(principal.getName());
			model.addAttribute("user", vo);
		}

	}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(User2VO vo) {
		service.insertUser2(vo);
		return "redirect:/user2/login";
	}
}
```

### 3. Service

```java
package kr.co.ch08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ch08.repository.User2Repo;
import kr.co.ch08.vo.User2VO;

@Service
public class User2Service {
	
	@Autowired
	private User2Repo repo;
	
	public void insertUser2(User2VO vo) {
		
		// Spring Security 권장 Password 암호화
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		vo.setPass(passEncoder.encode(vo.getPass()));
		
		
		repo.save(vo);
	}
	
	public User2VO selectUser2(String uid, String pass) {
		return repo.findUser2VOByUidAndPass(uid, pass);
	}
	
	public User2VO selectUser2(String uid) {
		return repo.findById(uid).get();
	}

}
```

### 4. Spring Security 환경설정

1. WebSecurityConfigurerAdapter를 상속받아서 구현하는 방법(권장 X)

```java
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

// WebSecurityConfigurerAdapter를 상속받아서 구현하는 방법은 현재 권장되지 않는다.

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecurityUserService service;
	
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
	
	
}

```

2. @Bean을 통한 구현하는 방법(권장 O)

- 빈으로 등록하는 경우에는 위의 주석으로 달린 로그인 인증처리 서비스 등록이 자동으로 됨
- PasswordEncoder  passwordEncoder() 를 선언해주면 해당 메서드를 통해 인코딩함

```java
package kr.co.ch08.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig2 {
	
	@Autowired
	private SecurityUserService service;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll(); // 모든 자원에 대해서 모든 사용자 접근 허용
		http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN"); // admin 하위 모든 링크에 대해서 ADMIN에게만 접근 허용 
		http.authorizeHttpRequests().antMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER"); // member 하위 모든 링크에 대해서 ADMIN, MEMBER에게만 접근 허용
		http.authorizeHttpRequests().antMatchers("/user2/loginSuccess").hasRole("ADMIN"); // /user2/loginSuccess는 ADMIN만 접근 허용
		
		// 자동 로그인 설정
		http.rememberMe()
			.key("autoUser")
			.rememberMeParameter("autoUid")
			.tokenValiditySeconds(600)
			.userDetailsService(service);
			
		// 사이트 위변조 요청 방지
		http.csrf().disable();
		
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
  public PasswordEncoder  PasswordEncoder () {
       return new BCryptPasswordEncoder();
  }
	
}
```

### 5. UserDetailService

```java
package kr.co.ch08.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.ch08.repository.User2Repo;
import kr.co.ch08.security.MyUserDetails.MyUserDetailsBuilder;
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
								.roles("MEMBER")
								.build();
		
		
    /* 아래의 방법은 UserDetails를 implements한뒤에 커스텀하여 구현하는 방법*/
		// 커스텀하는 이유 : 사용자 정보(아이디, 비밀번호, 이름, 나이...)를 가져오기위해

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
									.age(user.getAge())
									.rdate(user.getRdate())
									.build();
		
		System.out.println("loadUserByUsername...실행");
		
		return myUser2;
	}

}
```

### 6. UserDetail 커스텀

```java
package kr.co.ch08.security;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String uid;
	private String pass;
	private String name;
	private int grade;
	private String hp;
	private int age;
	private LocalDateTime rdate;
	
	
	public void roles(String... grade) {
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 계정이 갖는 권한 목록
		
		return null;
	}

	@Override
	public String getPassword() {
		// 계정이 갖는 비밀번호
		return pass;
	}

	@Override
	public String getUsername() {
		// 계정이 갖는 아이디
		return uid;
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료 여부(true: 만료X, false: 만료O)
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠김 여부(true: 잠김X, false: 잠김O)
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 계정 비밀번호 만료여부(true: 만료X, false: 만료O)
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화 여부(true: 활성화, false: 비활성화)
		return true;
	}

}

```

### 7. 사용자 정보 화면 출력

```java
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
	  xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
	<head>
		<meta charset="UTF-8">
		<title>user2::loginSuccess</title>
	</head>
	<body>
		<h3>user2 loginSuccess</h3>
		<div sec:authorize="isAuthenticated()">
			<h4>방법1</h4>
			<p >
				아이디: <span>[[${#authentication.principal.uid}]]</span><br/>
				이름: <span>[[${#authentication.principal.name}]]</span><br/>
				휴대폰: <span sec:authentication="principal.hp">휴대폰번호</span><br/>
				나이: <span sec:authentication="principal.age">나이</span><br/>
				가입일: <span sec:authentication="principal.rdate">가입일</span><br/>
				권한: <span sec:authentication="principal.authorities">권한</span><br/>
				<a th:href="@{/user2/logout}">로그아웃</a>
			</p>
		</div>
		<div th:if="${user ne null}">
			<h4>방법2</h4>
			<p >
				아이디: <span>[[${user.uid}]]</span><br/>
				이름: <span>[[${user.name}]]</span><br/>
				휴대폰: <span th:text="${user.hp}">휴대폰번호</span><br/>
				나이: <span th:text="${user.age}">나이</span><br/>
				가입일: <span th:text="${user.rdate}">가입일</span><br/>
				<a th:href="@{/user2/logout}">로그아웃</a>
			</p>
		</div>
		<p sec:authorize="isAnonymous()">
			로그인을 해야합니다. <a th:href="@{/user2/login}">로그인</a>
		</p>
	</body>
</html>
```

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/15d599ac-cc59-4c42-b6f2-293bec872650/Untitled.png)
