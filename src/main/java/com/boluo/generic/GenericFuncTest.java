package com.boluo.generic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chao
 * @date 2023/2/21 22:41
 * @desc
 */
public class GenericFuncTest {

    public static void main(String[] args) {
        genericFunc("a", "b");
        genericFunc("a", 1);
        genericFunc("a", 'c');
        genericFunc("a", true);

        genericFunc("name1", "k1", "v1");
        genericFunc("name2", "k2", 2);
        genericFunc("name3", "k3", true);

        genericFunc2("a", 1, true, 18866887799L);
    }

    // 创建一个泛型方法
    public static <T> String genericFunc(String name, T t) {
        String res = name + t;
        System.out.println("连接两个参数的结果: " + res);
        return res;
    }

    public static <K, V> String genericFunc(String name, K k, V v) {
        String res = String.format("%s : {%s : %s}", name, k, v);
        System.out.println("参数处理结果: " + res);
        return res;
    }

    // 泛型中的可变参数
    public static <A> String genericFunc2(A... args) {
        List<String> collect = Arrays.stream(args).map(Object::toString).collect(Collectors.toList());
        String res = String.join(",", collect);
        System.out.println("传入的参数为: " + res);
        return res;
    }

}
