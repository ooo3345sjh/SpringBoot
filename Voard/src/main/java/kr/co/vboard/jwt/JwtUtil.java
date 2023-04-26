package kr.co.vboard.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JComboBox.KeySelectionManager;

import org.hibernate.cache.spi.CacheKeysFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Component
public class JwtUtil {
	
	private final long TOKEN_VALIDATE_TIME = 1000 * 60 * 60 * 1; // 1시간
	
	
	private SecretKey secretKey;
	
	public JwtUtil(@Value("${jwt.secret}") String secret) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	
	}
	
	
	
	// jwt 클레임 추출
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
		Claims clamis  = Jwts.parserBuilder()
								.setSigningKey(secretKey)
								.build()
								.parseClaimsJws(token)
								.getBody();
		return claimsResolver.apply(clamis);
	}
	
	
	// 사용자 ID 추출
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	// 토큰 유효성 검증
	public boolean validateToken(String roken) {
		Date expiration = getClaimFromToken(roken, Claims::getExpiration);
		return !expiration.before(new Date());
	}
	
	// HTTP 헤더에서 토큰 추출
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	
	// JWT 생성
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		Date createDate = new Date();
		Date exprireDate = new Date(createDate.getTime() + TOKEN_VALIDATE_TIME);
		
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(createDate)
				.setExpiration(exprireDate)
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();

	}

}
