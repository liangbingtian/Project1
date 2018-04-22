package com.example.latte.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liangbingtian on 2018/3/29.
 */

@Target(ElementType.TYPE)   //告诉我们该注解用在该类上
@Retention(RetentionPolicy.SOURCE)   //告诉我们该注解是在源码期间处理的
public @interface EntryGenerator {

    String packageName();

    Class<?> entryTemplete();

}
