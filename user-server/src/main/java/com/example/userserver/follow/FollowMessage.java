package com.example.userserver.follow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowMessage {
	private int userId;
	private int followerId;
	// follow 하는건지 unfollow 하는건지
	private boolean follow;
}
