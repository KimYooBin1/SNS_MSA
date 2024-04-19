package sns.feedserver.feed;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialFeedService {
	private final SocialFeedRepository feedRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	@Value("${sns.user-server}")
	private String userServerUrl;
	private RestClient restClient = RestClient.create();

	public List<SocialFeed> getAllFeeds(){
		return feedRepository.findAll();
	}
	public List<SocialFeed> getAllFeedsByUploaderId(int uploaderId){
		return feedRepository.findByUploaderId(uploaderId);
	}

	public SocialFeed getFeedById(int feedId){
		return feedRepository.findById(feedId).orElse(null);
	}

	@Transactional
	public void deleteFeed(int feedId){
		feedRepository.deleteById(feedId);
	}

	@Transactional
	public SocialFeed createFeed(FeedRequest feed){
		SocialFeed savedFeed = feedRepository.save(new SocialFeed(feed));
		UserInfo uploader = getUserInfo(savedFeed.getUploaderId());
		// kafka 적용
		FeedInfo feedInfo = new FeedInfo(savedFeed, uploader.getUsername());
		try{
			kafkaTemplate.send("feed.created", objectMapper.writeValueAsString(feedInfo));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return savedFeed;
	}

	public void refreshAllFeeds(){
		List<SocialFeed> feeds = getAllFeeds();
		for (SocialFeed feed : feeds) {
			UserInfo uploader = getUserInfo(feed.getUploaderId());
			FeedInfo feedInfo = new FeedInfo(feed, uploader.getUsername());
			try {
				kafkaTemplate.send("feed.created", objectMapper.writeValueAsString(feedInfo));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// Feed Server 에서 User Server 호출
	public UserInfo getUserInfo(int userId) {
		return restClient.get()
			.uri(userServerUrl + "/api/users/" + userId)
			.retrieve()
			.onStatus(HttpStatusCode::isError, (request, response) -> {
				throw new RuntimeException("invalid server response " + response.getStatusText());
			})
			.body(UserInfo.class);
	}
}
