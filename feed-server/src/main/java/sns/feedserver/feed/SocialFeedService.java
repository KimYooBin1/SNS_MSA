package sns.feedserver.feed;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialFeedService {
	private final SocialFeedRepository feedRepository;

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
		return feedRepository.save(new SocialFeed(feed));
	}
}
