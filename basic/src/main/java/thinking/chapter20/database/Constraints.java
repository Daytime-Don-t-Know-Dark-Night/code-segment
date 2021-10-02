package thinking.chapter20.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author dingc
 * @Date 2021/10/2 20:09
 * @Description This is description of class
 * @Since version-1.0
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
	boolean primaryKey() default false;

	boolean allowNull() default true;

	boolean unique() default false;
}

