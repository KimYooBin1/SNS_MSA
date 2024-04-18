package sns.feedserver.feed;

import lombok.Data;

@Data
public class FeedRequest {
	private String imageId;
	private int uploaderId;
	private String contents;
}
