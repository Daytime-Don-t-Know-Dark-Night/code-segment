package concurrent;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author chao
 * @date 2023/1/14 22:02
 * @desc
 */
public class AsynchronousTest {

    private static final Long MAX_SLEEP_TIME = 10L;

    // https://www.cnblogs.com/cscw/p/14095819.html

    public static void main(String[] args) throws InterruptedException {

        List<String> tableNames = Lists.newArrayList("tableA", "tableB", "tableC", "tableD", "tableE", "tableF");

        // 串行方式
        // func1(tableNames);

        // 并行方式
        func2(tableNames);
    }

    public static void check(String tableName) throws InterruptedException {
        System.out.println("start check: " + tableName);
        for (int i = 0; i < MAX_SLEEP_TIME; i++) {
            System.out.println("checking: " + tableName);
            Thread.sleep(1000L);
        }
        System.out.println("check " + tableName + " end");
    }

    public static void func1(List<String> tableNames) throws InterruptedException {
        for (String tableName : tableNames) {
            check(tableName);
        }
    }

    public static void func2(List<String> tableNames) throws InterruptedException {
        // 方法带返回值: CompletableFuture.supplyAsync(()-> {return check();});
        // 方法不带返回值: CompletableFuture.runAsync(() -> check());
        tableNames.parallelStream().forEach(i -> CompletableFuture.runAsync(() -> {
            try {
                check(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).join());
    }

}
