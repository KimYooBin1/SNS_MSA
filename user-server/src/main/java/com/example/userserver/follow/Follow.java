package com.example.userserver.follow;

import static jakarta.persistence.GenerationType.*;
import static java.time.LocalDateTime.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Follow {
	@Id @GeneratedValue(strategy = IDENTITY)
	private int followId;
	//팔로우 받는 유저
	private int userId;
	//팔로우 하는 유저
	private int followerId;
	private LocalDateTime followDatetime;

	public Follow(int userId, int followerId) {
		this.userId = userId;
		this.followerId = followerId;
	}

	@PrePersist
	protected void onCreate(){
		followDatetime = now();
	}
}
