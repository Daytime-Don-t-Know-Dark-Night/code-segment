package java8.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author chao
 * @datetime 2024-03-15 23:33
 * @description
 */
public class Dog extends Animal {


    public void func1() {
        System.out.println(super.str1);
        super.func1();
    }

    public static void a() {
        List<Integer> list = Lists.newArrayList(1, 2, 3);
        b(list);
        System.out.println(list);
    }

    public static void b(List<Integer> list) {
        list.add(4);
    }

    public static void main(String[] args) {
        a();
    }

}
