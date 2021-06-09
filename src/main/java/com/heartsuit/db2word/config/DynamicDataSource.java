package com.heartsuit.db2word.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 18:06
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getDataSourceType();
    }
}
