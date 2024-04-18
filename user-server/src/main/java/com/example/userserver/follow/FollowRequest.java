package com.example.userserver.follow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowRequest {
	private int userId;
	private int followerId;
}
