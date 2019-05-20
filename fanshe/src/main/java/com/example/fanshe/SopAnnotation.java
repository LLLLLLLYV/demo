package com.example.fanshe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)  //注解的生命周期保存RUNTIME时
@Target({ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})//注解使用的范围（可以是方法，也可以是类）
public @interface SopAnnotation
{
    String color();  //注解的一个属性   <br>　　　　 String name() default "zhangsan";  //带默认值的属性
}