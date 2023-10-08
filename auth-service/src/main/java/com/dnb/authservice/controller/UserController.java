package com.dnb.authservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnb.authservice.dto.User;
import com.dnb.authservice.exceptions.IdNotFoundException;
import com.dnb.authservice.service.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable("userId") Integer userId) throws IdNotFoundException {

		Optional<User> user = userService.findById(userId);

		if (user.isEmpty())
			throw new IdNotFoundException("UserId Not Found!!");

		return ResponseEntity.ok(user.get());
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteByUserById(@PathVariable("userId") Integer userId) throws IdNotFoundException {

		if (!userService.existsById(userId))
			throw new IdNotFoundException("UserId not found!!");
		userService.deleteByUserId(userId);
		return ResponseEntity.noContent().build();
	}
}
