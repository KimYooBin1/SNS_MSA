package com.example.notificationbatch;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationInfo {
	private int followId;
	private String email;
	private String username;
	private String followerName;
	private int followerId;
	private LocalDateTime followDateTime;

}
