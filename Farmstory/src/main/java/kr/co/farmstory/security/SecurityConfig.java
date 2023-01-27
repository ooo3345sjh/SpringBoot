package kr.co.farmstory.security;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			// 사이트 위변조 요청 방지
			.csrf().disable()
			
			// 인가(접근권한) 설정
			.authorizeHttpRequests(req -> 
				req.antMatchers("/").permitAll()
				.antMatchers("/user/*").hasRole("ANONYMOUS")
				.antMatchers("/gnb/write", "/gnb/modify", "/gnb/delete").authenticated()
			)

			// 로그인 설정
			.formLogin(login ->
				login.loginPage("/user/login")
				.defaultSuccessUrl("/")
				.failureUrl("/user/login?success=100")
				.passwordParameter("pass")
				.usernameParameter("uid")
			)
			// 로그인 아웃 설정
			.logout(logout ->
				logout.invalidateHttpSession(true)
				.logoutUrl("/user/logout")
				.logoutSuccessUrl("/user/login")
			)
			
			// 에러 설정
			.exceptionHandling(e->e.accessDeniedPage("/user/terms"));
		
		return http.build();
	}
	@Bean
	RoleHierarchy roleHierarchy(){
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER"); // ADMIN은 USER의 권한을 포함한다.
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
	
}
