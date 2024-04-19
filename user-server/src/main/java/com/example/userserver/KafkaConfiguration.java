package com.example.userserver;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
	@Bean
	public NewTopic userFollowerTopic(){
		//partition 1개에 replicas 1개로 설정
		return new NewTopic("user.follower", 1, (short)1);
	}
}
