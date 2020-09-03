/**
 * Date: 2020-09-02 18:21
 * Author: xupp
 */

package com.xupp.springbootmybatis.config;

public class MultipleDataSourceHelper {

    public static final String MASTER = "master";
    public static final String SLAVE = "slave";

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void set(String db) {
        contextHolder.set(db);
    }

    public static String get() {
        return contextHolder.get();
    }

}