package sns.feedserver.feed;

import static jakarta.persistence.GenerationType.*;
import static java.time.LocalDateTime.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table @Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class SocialFeed {

	@Id @GeneratedValue(strategy = IDENTITY)
	private int feedId;

	private String imageId;

	private int uploaderId;

	private LocalDateTime uploadDatetime;

	private String contents;

	@PrePersist
	protected void onCreate(){
		uploadDatetime = now();
	}

	public SocialFeed(String imageId, int uploaderId, String contents) {
		this.imageId = imageId;
		this.uploaderId = uploaderId;
		this.contents = contents;
	}

	public SocialFeed(FeedRequest request) {
		this(request.getImageId(), request.getUploaderId(), request.getContents());
	}
}
