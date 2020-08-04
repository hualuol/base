package com.hlwd.util.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource() {
    }

    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSource();
    }
}

