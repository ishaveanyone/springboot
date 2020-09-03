/**
 * Date: 2020-09-02 18:22
 * Author: xupp
 */

package com.xupp.springbootmybatis.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return MultipleDataSourceHelper.get();
    }
}
