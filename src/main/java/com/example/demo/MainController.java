package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public @ResponseBody User getUserById(@PathVariable Integer id) {
        return userRepository.findOne(id);
    }

    @GetMapping("/user/name/{username}")
    public @ResponseBody User getUserByUsername(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}

	@GetMapping("/user/version/{v}")
	public Iterable<User> getUserByVersion(@PathVariable Integer v) {
		return userRepository.findByVersion(v);
	}
}
