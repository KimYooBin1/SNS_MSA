package com.example.userserver.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.userserver.user.UserInfo;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
	Follow findByUserIdAndFollowerId(int userId, int followerId);

	// 해당 유저 팔로워
	@Query(value = "select new com.example.userserver.user.UserInfo(u) FROM  Follow  f, User u "
		+ "where f.userId = :userId and u.userId = f.followerId")
	List<UserInfo> findFollowersByUserId(@Param("userId") int userId);

	// 해당 유저가 팔로우 하는 유저
	@Query(value = "select new com.example.userserver.user.UserInfo(u) FROM  Follow  f, User u "
		+ "where f.followerId = :userId and u.userId = f.userId")
	List<UserInfo> findFollowingByUserId(@Param("userId") int userId);
}
