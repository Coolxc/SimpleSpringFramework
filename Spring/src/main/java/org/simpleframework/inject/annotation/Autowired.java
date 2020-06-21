package org.simpleframework.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) //Just support filed autowired
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    String value() default "";
}
