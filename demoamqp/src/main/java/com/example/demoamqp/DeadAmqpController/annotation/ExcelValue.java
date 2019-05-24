package com.example.demoamqp.DeadAmqpController.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  //注解的生命周期保存RUNTIME时
@Target(ElementType.FIELD)
public @interface ExcelValue {
    String name ();
}
