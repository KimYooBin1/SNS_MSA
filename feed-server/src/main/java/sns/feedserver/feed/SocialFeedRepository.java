package sns.feedserver.feed;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialFeedRepository extends JpaRepository<SocialFeed, Integer> {
	List<SocialFeed> findByUploaderId(int id);
}
