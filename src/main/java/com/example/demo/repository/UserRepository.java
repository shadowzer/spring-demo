package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByUsername(String username);

	@Query("select u from User u where u.version = ?1 order by u.id desc")
	List<User> findByVersion(Integer version);
}
