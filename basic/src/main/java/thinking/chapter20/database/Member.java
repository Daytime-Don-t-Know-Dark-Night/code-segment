package thinking.chapter20.database;

/**
 * @Author dingc
 * @Date 2021/10/2 20:17
 * @Description This is a java bean
 * @Since version-1.0
 */
@DBTable(name = "member")
public class Member {
	@SQLString(30)
	String firstName;

	@SQLString(50)
	String lastName;

	@SQLInteger
	Integer age;

	@SQLString(value = 30, constraints = @Constraints(primaryKey = true))
	String handle;

	static int memberCount;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getAge() {
		return age;
	}

	public String getHandle() {
		return handle;
	}

	public String toString() {
		return handle;
	}
}
