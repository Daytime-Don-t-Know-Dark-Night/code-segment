package boluo.reflex;

public class Target1 {

	public int func11() {
		return 0;
	}

	public void func12() {
		System.out.println("1-2");
	}

	public String func13(String params) {
		return params + ": invoke";
	}

	public String func14(String... params) {
		return params[0];
	}

}
