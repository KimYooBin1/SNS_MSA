package com.example.userserver.follow;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userserver.user.UserInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {
	private final FollowService followService;

	/**
	 * 자기를 follow 하는 사람들
	 */
	@GetMapping("/followers/{userId}")
	public List<UserInfo> listFollowers(@PathVariable int userId) {
		return followService.listFollower(userId);
	}

	/**
	 * 자기가 follow 하는 사람들
	 */
	@GetMapping("/followings/{userId}")
	public List<UserInfo> listFollowings(@PathVariable int userId) {
		return followService.listFollowing(userId);
	}

	@GetMapping("/follow/{userId}/{followerId}")
	public boolean isFollow(@PathVariable int userId, @PathVariable int followerId) {
		return followService.isFollow(userId, followerId);
	}

	@PostMapping("/follow")
	public Follow followUser(@RequestBody FollowRequest request) {
		return followService.followUser(request.getUserId(), request.getFollowerId());
	}

	@PostMapping("/unfollow")
	public Boolean unfollowUser(@RequestBody FollowRequest request) {
		return followService.unfollowUser(request.getUserId(), request.getFollowerId());
	}
}
