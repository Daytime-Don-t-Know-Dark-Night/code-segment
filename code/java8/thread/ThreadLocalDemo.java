package java8.thread;

import java8.utils.ThreadUtils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chao
 * @datetime 2024-11-18 18:44
 * @description
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = ThreadUtils.getMinThreadPool();
        String sites[] = {"A", "B", "C", "D", "E", "F", "G"};
        for (String site : sites) {
            executor.execute(() -> test(site));
        }

    }

    public static void test(String site) {
        while (true) {
            System.out.println(site);
        }
    }

}
