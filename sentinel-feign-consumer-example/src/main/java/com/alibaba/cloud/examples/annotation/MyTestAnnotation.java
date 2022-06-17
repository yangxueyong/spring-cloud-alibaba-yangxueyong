package com.alibaba.cloud.examples.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyTestAnnotation {
    String value() default "";
}