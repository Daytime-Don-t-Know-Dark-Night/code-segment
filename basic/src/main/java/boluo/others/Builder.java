package boluo.others;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * general pattern builder
 *
 * @author dingc
 * @since 2021-09-25
 */

public class Builder<T> {

	private final Supplier<T> instantiator;
	private List<Consumer<T>> modifiers = new ArrayList<>();

	public Builder(Supplier<T> instantiator) {
		this.instantiator = instantiator;
	}

	public static <T> Builder<T> of(Supplier<T> instantiator) {
		return new Builder<>(instantiator);
	}

	public <P1> Builder<T> with(Consumer1<T, P1> consumer, P1 p1) {
		Consumer<T> c = instance -> consumer.accept(instance, p1);
		modifiers.add(c);
		return this;
	}

	public <P1, P2> Builder<T> with(Consumer2<T, P1, P2> consumer, P1 p1, P2 p2) {
		Consumer<T> c = instance -> consumer.accept(instance, p1, p2);
		modifiers.add(c);
		return this;
	}

	public <P1, P2, P3> Builder<T> with(Consumer3<T, P1, P2, P3> consumer, P1 p1, P2 p2, P3 p3) {
		Consumer<T> c = instance -> consumer.accept(instance, p1, p2, p3);
		modifiers.add(c);
		return this;
	}

	public T build() {
		T value = instantiator.get();
		modifiers.forEach(modifier -> modifier.accept(value));
		modifiers.clear();
		return value;
	}

	@FunctionalInterface
	public interface Consumer1<T, P1> {
		void accept(T t, P1 p1);
	}

	@FunctionalInterface
	public interface Consumer2<T, P1, P2> {
		void accept(T t, P1 p1, P2 p2);
	}

	@FunctionalInterface
	public interface Consumer3<T, P1, P2, P3> {
		void accept(T t, P1 p1, P2 p2, P3 p3);
	}

}

