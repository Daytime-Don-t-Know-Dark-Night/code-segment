package com.boluo.utils;

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
