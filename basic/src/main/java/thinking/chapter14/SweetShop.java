package thinking.chapter14;

class Candy {
	static {
		System.out.println("Loading Candy");
	}
}

class Gum {
	static {
		System.out.println("Loading Gum");
	}
}

class Cookie {
	static {
		System.out.println("Loading Cookie");
	}
}

public class SweetShop {

	// Class对象仅在需要的时候才被加载, static初始化是在类加载时进行的
	public static void main(String[] args) {
		System.out.println("insert main");
		new Candy();
		System.out.println("after creating candy");

		try {
			Class<?> c = Class.forName("thinking.chapter14.Gum");
		} catch (ClassNotFoundException e) {
			System.out.println("Couldn't find Gum");
		}
		System.out.println("after class.forName(Gum)");
		new Cookie();
		System.out.println("after creating cookie");
	}
}
