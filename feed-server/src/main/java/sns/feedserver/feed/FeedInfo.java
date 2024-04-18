package sns.feedserver.feed;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedInfo {
	private int feedId;
	private String imageId;
	private int uploaderId;
	private String uploaderName;
	private LocalDateTime uploadDatetime;
	private String contents;

	public FeedInfo(SocialFeed feed, String uploaderName) {
		this(feed.getFeedId(), feed.getImageId(), feed.getUploaderId(), uploaderName, feed.getUploadDatetime(),
			feed.getContents());
	}
}
