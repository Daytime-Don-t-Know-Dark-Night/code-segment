package thinking.chapter20.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author dingc
 * @Date 2021/10/2 20:10
 * @Description This is description of class
 * @Since version-1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString {
	int value() default 0;

	String name() default "";

	Constraints constraints() default @Constraints;
}
