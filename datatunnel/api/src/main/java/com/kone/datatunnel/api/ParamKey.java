package com.kone.datatunnel.api;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 灵活处理各种不同命名约定的配置参数，同时
 * 保证内部代码的一致性
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD})
public @interface ParamKey {
    String value();
}