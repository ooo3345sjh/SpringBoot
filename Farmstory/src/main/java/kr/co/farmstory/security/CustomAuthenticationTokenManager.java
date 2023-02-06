package kr.co.farmstory.security;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class CustomAuthenticationTokenManager implements AuthenticationProvider {

    @Autowired
    private UserRepo repo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthenticationToken token = (CustomAuthenticationToken)authentication;
        UserEntity user = repo.findById(token.getUserName()).orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다."));

        String inputPass = token.getPassword(); // 입력된 패스워드
        String dbPass = user.getPass(); // DB에 저장된 패스워드
        if(inputPass.equals(dbPass)){
            user.setPass(null);
            WebAuthenticationDetails details = (WebAuthenticationDetails)(authentication.getDetails());

            return CustomAuthenticationToken.builder()
                        .principal(user)
                        .details(details)
                        .authenticated(true)
                        .build();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == CustomAuthenticationToken.class;
    }
}
