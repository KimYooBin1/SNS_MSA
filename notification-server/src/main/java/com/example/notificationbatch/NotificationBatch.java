package com.example.notificationbatch;

import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class NotificationBatch {
	@Bean
	public JdbcCursorItemReader<NotificationInfo> reader(DataSource source) {
		return new JdbcCursorItemReaderBuilder<NotificationInfo>()
			.name("followerReader")
			.dataSource(source)
			.sql("""
				select f.follow_id, u.email, u.username, f.follower_id, u2.username as follower_name, f.follow_datetime
				from follow f, user u, user u2
				where f.user_id = u.user_id and f.follower_id = u2.user_id and f.mail_sent_datetime is null;
				""")
			.rowMapper(new BeanPropertyRowMapper<>(NotificationInfo.class))
			.build();
	}

	@Bean
	public ItemWriter<NotificationInfo> sendMail(JavaMailSender mailSender, JdbcTemplate jdbcTemplate) {
		return items -> {
			for (NotificationInfo item : items) {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom("kyb1208tg@gmail.com");
				message.setTo(item.getEmail());
				message.setSubject("New Follower!");
				message.setText(
					"Hello, " + item.getUsername() + "! @" + item.getFollowerName() + " is now follow you!");
				mailSender.send(message);

				jdbcTemplate.update("update follow set mail_sent_datetime = ? where follow_id = ?", now(),
					item.getFollowerId());
			}
		};
	}

	@Bean
	public Step notificationStep(JobRepository jobRepository,
								 PlatformTransactionManager transactionManager,
								 ItemReader<NotificationInfo> itemReader,
								 ItemWriter<NotificationInfo> itemWriter) {
		return new StepBuilder("mail-send-step", jobRepository)
			.<NotificationInfo, NotificationInfo>chunk(10, transactionManager)
			.reader(itemReader)
			.writer(itemWriter)
			.build();
	}

	@Bean
	public Job notificationJob(Step notification, JobRepository jobRepository) {
		return new JobBuilder("mail-send-job", jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(notification)
			.build();
	}
}
