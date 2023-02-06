package kr.co.farmstory.security;

import kr.co.farmstory.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomAuthenticationToken implements Authentication {

    private UserEntity principal;
    private String  credentials;
    private Object details;
    private boolean authenticated;
    private String userName;
    private String password;

    public CustomAuthenticationToken(String userName, String password){
        this.userName = userName;
        this.password = password;
        setAuthenticated(false);
    }

    public void setDetailes(String remoteAddress, String sessionId){
        WebAuthenticationDetails details = new WebAuthenticationDetails(remoteAddress, sessionId);
        this.details = details;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(principal != null){
            // 계정이 갖는 권한 목록
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + principal.getGrade()));
            return authorities;
        }

        return new HashSet<>();
    }

    @Override
    public String getName() {
        return principal == null ? "" : principal.getUsername();
    }
}
