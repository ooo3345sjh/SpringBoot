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
			// ????????? ????????? ?????? ??????
//			.csrf().disable()

			// ??????(????????????) ??????
			.authorizeHttpRequests(req ->
				req.mvcMatchers("/user/logout").permitAll()
				.mvcMatchers("/user/**").hasRole("ANONYMOUS")
				.mvcMatchers("/gnb/write", "/gnb/modify", "/gnb/delete").authenticated()
				.mvcMatchers("/", "/index", "/gnb/**", "/auth", "/user/**").permitAll()
				.antMatchers(HttpMethod.GET, "/comment/**").permitAll()
				.anyRequest().authenticated()
			)

			// ????????? ??????
			.formLogin(login ->
				login.loginPage("/user/login")      // ????????? ????????? ?????? ??????
				.loginProcessingUrl("/user/login")  // POST??? ????????? ????????? ?????? ??? ??????
				.defaultSuccessUrl("/")				// ???????????? ????????? ??? ??????
				.successHandler(authenticationSuccessHandler)
				.failureUrl("/user/login?success=100")
				.passwordParameter("pass")
				.usernameParameter("uid")
			)
			// ????????? ?????? ??????
			.logout(logout ->
				logout.invalidateHttpSession(true)
				.logoutUrl("/user/logout")
				.logoutSuccessHandler((req, resp, auth) -> {
					resp.sendRedirect(req.getHeader("Referer"));
				})
			)

			// ?????? ????????? ??????
			.rememberMe(remem -> remem.tokenValiditySeconds(60000)
//						 .tokenValiditySeconds(600)
//						 .rememberMeServices(new TokenBasedRememberMeServices("autoUser", securityUserService){
//							 @Override
//							 protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
//								 return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//							 }
//						 })
							.rememberMeServices(rememberMeServices())

			)

			
			// ?????? ??????
			.exceptionHandling(e->e.accessDeniedPage("/accessDeniedPage"));

		// ???????????? AuthenticationProvider??? ?????? ???????????? ??????
		AuthenticationManagerBuilder amb = http.getSharedObject(AuthenticationManagerBuilder.class);
		amb.authenticationProvider(customAuthenticationTokenManager)
			.userDetailsService(securityUserService) // ????????? ?????? DaoAuthenticationProvider??? ???????????????????????? UserDetailsService ????????? ??????
		;
		return http.build();
	}
	@Bean
	RoleHierarchy roleHierarchy(){
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_2 > ROLE_1"); // 2??? 1??? ????????? ????????????.
		roleHierarchy.setHierarchy("ROLE_3 > ROLE_2"); // 3??? 2??? ????????? ????????????.
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
					// ????????? ?????? ????????? ????????? ?????? ????????? ??????????????? ?????? ????????? ???????????? ?????? ?????????
					// accessDeniedPage??? ????????????????????? ??????
					// RemembermeToken??? CustomAuthenticationToken?????? ????????? ????????? ????????????.
					// ????????? ??????????????? Provider??? token??? ??????????????? ????????????????????????.
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
