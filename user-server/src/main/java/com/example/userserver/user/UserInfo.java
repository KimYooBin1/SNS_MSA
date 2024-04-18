package com.example.userserver.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
	private int userId;
	private String username;
	private String email;

	public UserInfo(User user) {
		this(user.getUserId(), user.getUsername(), user.getEmail());
	}
}
