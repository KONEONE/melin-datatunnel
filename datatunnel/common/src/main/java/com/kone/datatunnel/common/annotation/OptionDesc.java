package com.kone.datatunnel.common.annotation;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 描述：生成javadoc
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD})
public @interface OptionDesc {
    String value();
}
