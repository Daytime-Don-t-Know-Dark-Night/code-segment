package com.boluo.enums;

/**
 * @author chao
 * @date 2022/11/11 21:56
 * @desc
 */
public class TypeTest {

    public static void main(String[] args) {
        Types.Daily[] values = Types.Daily.values();
        String tableName1 = values[0].getTableName();
        String tableName2 = values[1].getTableName();
        String tableName3 = values[2].getTableName();
        System.out.println(tableName1);
        System.out.println(tableName2);
        System.out.println(tableName3);
    }

}
