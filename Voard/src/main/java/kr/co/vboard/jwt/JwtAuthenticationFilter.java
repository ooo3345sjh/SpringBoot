package kr.co.vboard.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import kr.co.vboard.entity.UserEntity;
import kr.co.vboard.security.SecurityUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = jwtUtil.resolveToken(request);
		UserEntity user = null;
		
		log.info("filter1... : " + token);
		if(token != null && jwtUtil.validateToken(token)) {
			String uid = jwtUtil.getUsernameFromToken(token);
			log.info("filter2... : " + uid);
			
			// Security 인증처리
			user = (UserEntity)securityUserService.loadUserByUsername(uid);
			Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		// 다음 필터 이동
		filterChain.doFilter(request, response);
	}

}
