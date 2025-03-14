package com.boluo.reflex;

import java.lang.reflect.Field;

/**
 * @author chao
 * @date 2023/2/22 20:50
 * @desc
 */
public class FieldTest {

    // https://blog.csdn.net/m0_72041293/article/details/128050383#

    // 反射的功能:
    // 1. 在运行的时候判断任意一个对象所属的类
    // 2. 在运行时构造任意一个类的对象
    // 3. 在运行时得到任意一个类所具有的成员变量和方法
    // 4. 在运行时调用任意一个对象的成员变量和方法
    // 5. 生成动态代理

    // 1. java.lang.Class: 代表一个类, Class对象表示某个类加载后在堆中的对象
    // 2. java.lang.reflect.Method: 代表类的方法, Method对象表示某个类的方法
    // 3. java.lang.reflect.Field: 代表类的成员变量, Field对象表示某个类的成员变量


    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        func2();
    }

    // 1. 利用反射获取运行时对象中的变量 (指定变量名)
    public static void func1() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 模拟运行中的对象:
        Target1 t1 = new Target1();
        t1.setUserId(null);
        t1.setName("dingc");

        Class cls = Class.forName("java8.reflex.Target1");
        Field userId = cls.getField("userId");
        Field name = cls.getField("name");

        Object obj1 = userId.get(t1);
        Object obj2 = name.get(t1);

        System.out.println("userId value: " + obj1);
        System.out.println("name value: " + obj2);
    }

    // 2. 利用反射获取运行时对象中的所有变量
    public static void func2() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 模拟运行中的对象:
        Target1 t1 = new Target1();
        t1.setUserId(null);
        t1.setName("dingc");

        Class cls = Class.forName("java8.reflex.Target1");
        Field[] fields = cls.getFields();

        for (Field field : fields) {
            Object value = field.get(t1);
            System.out.println("属性{" + field.getName() + "}的值为: " + value);
        }
    }

}
