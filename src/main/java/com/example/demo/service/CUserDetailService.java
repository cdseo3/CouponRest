package com.example.demo.service;



import com.example.demo.repo.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CUserDetailService implements UserDetailsService {
	 
	private final UserJpaRepo userJpaRepo;
	
	public UserDetails loadUserByUsername(String userPk) {
		System.out.println("userPk++"+userPk);
        return userJpaRepo.findByMsrl(userPk);
    }
}
