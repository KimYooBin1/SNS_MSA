package sns.feedserver.feed;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class SocialFeedController {
	private final SocialFeedService feedService;

	@GetMapping
	public List<SocialFeed> getAllFeeds() {
		return feedService.getAllFeeds();
	}

	@GetMapping("/user/{userId}")
	public List<SocialFeed> getAllFeedsByUser(@PathVariable int userId){
		return feedService.getAllFeedsByUploaderId(userId);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SocialFeed> getFeedById(@PathVariable int id){
		SocialFeed feed = feedService.getFeedById(id);
		if(feed == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(feed);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteFeed(@PathVariable int id) {
		feedService.deleteFeed(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping
	public SocialFeed createFeed(@RequestBody FeedRequest feed){
		return feedService.createFeed(feed);
	}
}
