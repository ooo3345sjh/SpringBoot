package kr.co.sboard.security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.sboard.entity.UserEntity;
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
	
	private UserEntity user;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 계정이 갖는 권한 목록
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getGrade()));
		return authorities;
	}

	@Override
	public String getPassword() {
		// 계정이 갖는 비밀번호
		return user.getPass();
	}

	@Override
	public String getUsername() {
		// 계정이 갖는 아이디
		return user.getUid();
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
