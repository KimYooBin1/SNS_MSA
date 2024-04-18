package sns.feedserver.feed;

import static lombok.AccessLevel.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class UserInfo {
	private int userId;
	private String username;
	private String email;
}
