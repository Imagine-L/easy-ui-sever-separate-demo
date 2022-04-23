package top.liubaiblog.admin.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 刘佳俊
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    @AliasFor("title")
    String value() default "";

    @AliasFor("value")
    String title() default "";

    String businessType() default "";

}
