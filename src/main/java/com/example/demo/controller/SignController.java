package com.example.demo.controller;

import com.example.demo.api.security.JwtTokenProvider;
import com.example.demo.entity.User;
import com.example.demo.repo.UserJpaRepo;


import lombok.RequiredArgsConstructor;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/login")
public class SignController {
	
	
	    private final UserJpaRepo userJpaRepo;
	    private final JwtTokenProvider jwtTokenProvider;
	    private final PasswordEncoder passwordEncoder;

	    @PostMapping(value = "/signin")
	    public @ResponseBody Map<String,String> signin(@RequestBody Map<String,String> param) {
	    	
	    	String id = param.get("id");
	    	String password = param.get("password");
	    	Map<String,String> response = new HashMap<String, String>();
	    	
	        User user = userJpaRepo.findByUid(id);

	        if (!passwordEncoder.matches(password, user.getPassword())) {
	        	response.put("message","패스워드를 확인해 주세요");
		        response.put("status","FALSE");
		        
		        return response;
	        }
	        
	        response.put("message","로그인 완료");
	        response.put("token",jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
	        response.put("status","OK");
	            
	        return response;
	
		 }
	
		 @PostMapping(value = "/signup")
		 public @ResponseBody Map<String,String> signup(@RequestBody Map<String,String> param) {
			 
			String id = param.get("id");
		    String password = param.get("password");
		    String name = param.get("name");
		    	
		    
	        userJpaRepo.save(User.builder()
	                .uid(id)
	                .password(passwordEncoder.encode(password))
	                .name(name)
	                .roles(Collections.singletonList("ROLE_USER"))
	                .build());
	        Map<String,String> response = new HashMap<String, String>();
	        
	        response.put("message","가입 완료");
	        response.put("status","OK");
	        
	        return response;
	    }
}
