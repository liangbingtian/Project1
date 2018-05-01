package com.example.latte.util.callback;

/**
 * Created by liangbingtian on 2018/4/30.
 * 泛型接口使得我们以更安全的方式转换各种类型
 */

public interface IGlobalCallback<T> {

       void executeCallback(T args);
}
