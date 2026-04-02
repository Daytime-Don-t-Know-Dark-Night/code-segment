package com.boluo.utils;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chao
 * @datetime 2024-11-18 18:45
 * @description
 */
public class ThreadUtils {

    public static void main(String[] args) {
        System.out.println("AAa");

        int[] arr = new int[5];
        Arrays.stream(arr).forEach(System.out::println);


        int[] arr1 [];
        System.out.println(5/2);

        int x = 1, y = 2, z = 3;
        String s = "xyz";

        System.out.println(s + x + y + z);


    }


    public static ThreadPoolExecutor getMinThreadPool() {
        return new ThreadPoolExecutor(
                15,
                50,
                20,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }
}
