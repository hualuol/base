package com.hlwd.util.db;

public class DataSourceHolder {
    private static final ThreadLocal<String> dataSources = new ThreadLocal();

    public DataSourceHolder() {
    }

    public static void setDataSource(String customerType) {
        dataSources.set(customerType);
    }

    public static String getDataSource() {
        return (String)dataSources.get();
    }

    public static void clearDataSource() {
        dataSources.remove();
    }
}
