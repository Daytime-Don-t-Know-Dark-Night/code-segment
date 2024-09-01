package java8.utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author chao
 * @datetime 2024-03-01 23:17
 * @description
 */
public class Demo01 {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(13);
        list.add(99);


        List<String> list2 = new ArrayList<>();
        list2.add("a");
        list2.add("b");
        list2.add("c");
        boolean a = list2.remove("3");
        System.out.println(a);

//        for (int i = 0; i < list.size(); i++) {
//            list.remove(0);
//            i++;
//            System.out.println(list.get(i));
//        }


        Connection conn = null;
        // conn.setAutoCommit(false);


        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 5) {
                iterator.remove();
            }
        }

        System.out.println(list);

    }


}
