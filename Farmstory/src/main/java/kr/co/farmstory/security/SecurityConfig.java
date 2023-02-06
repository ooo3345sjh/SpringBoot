package kr.co.farmstory.security;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private RememberMeAuthenticationFilter filter;
	private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

	private final DataSource dataSource;
	private final SecurityUserService securityUserService;
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final CustomAuthenticationTokenManager customAuthenticationTokenManager;


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			// 사이트 위변조 요청 방지
//			.csrf().disable()
			
			// 인가(접근권한) 설정
			.authorizeHttpRequests(req ->
				req.mvcMatchers("/user/logout").permitAll()
				.mvcMatchers("/user/**").hasRole("ANONYMOUS")
				.mvcMatchers("/gnb/write", "/gnb/modify", "/gnb/delete").authenticated()
				.mvcMatchers("/", "/index", "/gnb/**", "/auth", "/user/**").permitAll()
				.antMatchers(HttpMethod.GET, "/comment/**").permitAll()
				.anyRequest().authenticated()
			)

			// 로그인 설정
			.formLogin(login ->
				login.loginPage("/user/login")      // 로그인 페이지 경로 설정
				.loginProcessingUrl("/user/login")  // POST로 로그인 정보를 보낼 시 경로
				.defaultSuccessUrl("/")				// 로그인이 성공할 시 경로
				.successHandler(authenticationSuccessHandler)
				.failureUrl("/user/login?success=100")
				.passwordParameter("pass")
				.usernameParameter("uid")
			)
			// 로그인 아웃 설정
			.logout(logout ->
				logout.invalidateHttpSession(true)
				.logoutUrl("/user/logout")
				.logoutSuccessHandler((req, resp, auth) -> {
					resp.sendRedirect(req.getHeader("Referer"));
				})
			)

			// 자동 로그인 설정
			.rememberMe(remem ->remem
//						 .tokenValiditySeconds(600)
//						 .rememberMeServices(new TokenBasedRememberMeServices("autoUser", securityUserService){
//							 @Override
//							 protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
//								 return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//							 }
//						 })
							.rememberMeServices(rememberMeServices())

			)

			
			// 에러 설정
			.exceptionHandling(e->e.accessDeniedPage("/accessDeniedPage"));

		// 커스텀한 AuthenticationProvider를 추가 등록하는 방법
		AuthenticationManagerBuilder amb = http.getSharedObject(AuthenticationManagerBuilder.class);
		amb.authenticationProvider(customAuthenticationTokenManager)
			.userDetailsService(securityUserService) // 기존에 있는 DaoAuthenticationProvider도 사용하기위해서는 UserDetailsService 등록이 필요
		;
		return http.build();
	}
	@Bean
	RoleHierarchy roleHierarchy(){
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_2 > ROLE_1"); // 2은 1의 권한을 포함한다.
		roleHierarchy.setHierarchy("ROLE_3 > ROLE_2"); // 3은 2의 권한을 포함한다.
		return roleHierarchy;
	}
	
	@Bean
    public PasswordEncoder PasswordEncoder () {
		return new BCryptPasswordEncoder();
    }
	
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
       return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	PersistentTokenRepository tokenRepository(){
		JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
		repository.setDataSource(dataSource);
		try{
			repository.removeUserTokens("1");
		}catch(Exception ex){
			repository.setCreateTableOnStartup(true);
		}
		return repository;
	}

	@Bean
	PersistentTokenBasedRememberMeServices rememberMeServices(){
		PersistentTokenBasedRememberMeServices service =
				new PersistentTokenBasedRememberMeServices("hello", securityUserService, tokenRepository())
				{
					// 권한이 없는 페이지 방문시 이미 로그인 되어있는데 다시 로그인 페이지로 가는 로직을
					// accessDeniedPage로 이동시키기위한 로직
					// RemembermeToken을 CustomAuthenticationToken으로 바꿔서 인증을 진행한다.
					// 그래서 추가적으로 Provider와 token을 커스텀해서 만들어주어야한다.
					@Override
					protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
						CustomAuthenticationToken token = new CustomAuthenticationToken(user.getUsername(), user.getPassword());
						token.setDetailes(request.getRemoteAddr(), request.getSession().getId());
						return token;
					}
				}
				;
		service.setAlwaysRemember(true);
		return service;
	}
}
