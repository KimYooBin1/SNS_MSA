package com.example.userserver.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	@PostMapping
	public UserInfo signUpUser(@RequestBody UserRequest request) {
		return userService.createUser(request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserInfo> getUserInfo(@PathVariable int id) {
		UserInfo user = userService.getUser(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<UserInfo> getUserInfoByName(@PathVariable String name) {
		UserInfo user = userService.getUserByName(name);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

	@PostMapping("/signIn")
	public UserInfo signIn(@RequestBody UserRequest request) {
		return userService.signIn(request);
	}
}
