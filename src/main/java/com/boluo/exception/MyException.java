package com.boluo.exception;

/**
 * @author chao
 * @date 2022/10/23 11:02
 * @desc
 */
public class MyException extends Exception {

    // 自定义异常一般包含两个构造方法, 无参默认构造方法和一个字符串参数的异常消息
    public MyException() {
        super();
    }

    public MyException(String s) {
        System.out.println("进入了MyException, 继续执行MyException中的代码");
    }

}

