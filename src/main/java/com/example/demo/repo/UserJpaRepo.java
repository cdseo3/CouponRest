package com.example.demo.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserJpaRepo extends JpaRepository<User, String> {
	public User findByName(String name);
	public User findByUid(String uid);
	//public List<User> findByUid(String uid);
	//like검색도 가능
	public List<User> findByNameLike(String keyword);
	
	public User findByMsrl(String msrl);
}
