package thinking.chapter20;

import java.util.List;

public class PasswordUtils {

	@UseCase(id = 47, description = "passwords must contain at least one numeric")
	public boolean validatePassword(String password) {
		return password.matches("\\w*\\d\\w*");
	}


	@UseCase(id = 48)
	public String encryptPassword(String password) {
		return new StringBuilder(password).reverse().toString();
	}

	@UseCase(id = 49, description = "new password can't equal previously used ones")
	public boolean checkForNewPassword(List<String> prevPasswords, String newPassword) {
		return !prevPasswords.contains(newPassword);
	}
}
