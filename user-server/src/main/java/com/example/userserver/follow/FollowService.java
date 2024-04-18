package com.example.userserver.follow;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userserver.user.UserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
	private final FollowRepository followRepository;

	public boolean isFollow(int userId, int followerId) {
		Follow follow = followRepository.findByUserIdAndFollowerId(userId, followerId);
		return follow != null;
	}

	@Transactional
	public Follow followUser(int userId, int followerId) {
		if (isFollow(userId, followerId)) {
			//이미 팔로우 상태
			return null;
		}
		return followRepository.save(new Follow(userId, followerId));
	}

	@Transactional
	public boolean unfollowUser(int userId, int followerId) {
		Follow follow = followRepository.findByUserIdAndFollowerId(userId, followerId);
		if(follow == null){
			//현재 언팔중
			return false;
		}
		followRepository.delete(follow);
		return true;
	}

	public List<UserInfo> listFollower(int userId){
		return followRepository.findFollowersByUserId(userId);
	}

	public List<UserInfo> listFollowing(int userId) {
		return followRepository.findFollowingByUserId(userId);
	}
}
