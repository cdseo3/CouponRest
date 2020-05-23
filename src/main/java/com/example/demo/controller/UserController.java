package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repo.UserJpaRepo;


@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/user")
public class UserController {
	
	
	@Autowired
	UserJpaRepo userJpaRepo;
	
	@PostMapping("/")
	public @ResponseBody List<User> createUser(@RequestBody Map<String,String> param){
		String name = param.get("name");
		String passWord = param.get("passWord");
		
		User user = User.builder()
                .name(name)
                .password(passWord)                
                .build();
		userJpaRepo.save(user);
		
		return userJpaRepo.findAll();
	}

}
