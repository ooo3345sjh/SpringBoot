package co.kr.farmstory.security;

import co.kr.farmstory.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SecurityUserService implements UserDetailsService {
	
//	@Autowired
//	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		return repo.findById(username).orElseThrow(()-> new UsernameNotFoundException(username));
		return null;
	}

}
