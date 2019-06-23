package com.hailin.shrine.jdbc.api.spi.masterslave;

import com.hailin.shrine.jdbc.api.TypeBasedSPI;

import java.util.List;

/**
 * 主从负载均衡算法
 */
public interface MasterSlaveLoadBalanceAlgorithm extends TypeBasedSPI {

    /**
     * 获取数据源
     *
     * @param name 主从逻辑数据源名称
     * @param masterDataSourceName name of master data sources
     * @param slaveDataSourceNames names of slave data sources
     */
    String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames);
}
