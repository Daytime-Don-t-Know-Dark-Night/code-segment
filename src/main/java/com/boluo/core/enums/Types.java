package com.boluo.core.enums;

/**
 * @author chao
 * @date 2022/11/11 21:56
 * @desc
 */
public class Types {

    enum Daily {
        daily_table1("table1"),
        daily_table2("table2"),
        daily_table3("table3");

        private final String tableName;

        Daily(String tableName) {
            this.tableName = tableName;
        }

        public String getTableName() {
            return tableName;
        }
    }

    enum Hourly {
        hourly_table1,
        hourly_table2,
        hourly_table3
    }

    enum Coa {
        coa_table1,
        coa_table2,
        coa_table3
    }

}
