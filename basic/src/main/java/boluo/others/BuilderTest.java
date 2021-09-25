package boluo.others;

import boluo.model.User;

public class BuilderTest {

	public static void main(String[] args) {

		User user = new User();
		user.setUid(1);
		user.setName("dingc");

		User user1 = Builder.of(User::new)
				.with(User::setUid, 1)
				.with(User::setName, "dingc")
				.build();
	}
}
