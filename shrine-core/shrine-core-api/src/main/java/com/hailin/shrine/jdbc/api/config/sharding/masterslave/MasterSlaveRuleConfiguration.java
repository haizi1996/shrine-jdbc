package com.hailin.shrine.jdbc.api.config.sharding.masterslave;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;

import java.util.Collection;

/**
 * 主从规则配置
 */
@Getter
public class MasterSlaveRuleConfiguration {

    //读写分离数据源名称
    private final String name;

    //主数据库名
    private final String masterDataSourceName;

    //从数据源名称集合
    private final Collection<String> slaveDataSourceNames;

    //负载均衡策略配置
    private final LoadBalanceStrategyConfiguration loadBalanceStrategyConfiguration;

    public MasterSlaveRuleConfiguration(final String name, final String masterDataSourceName, final Collection<String> slaveDataSourceNames) {
        this(name, masterDataSourceName, slaveDataSourceNames, null);
    }

    public MasterSlaveRuleConfiguration(final String name,
                                        final String masterDataSourceName, final Collection<String> slaveDataSourceNames, final LoadBalanceStrategyConfiguration loadBalanceStrategyConfiguration) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "Name is required.");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(masterDataSourceName), "MasterDataSourceName is required.");
        Preconditions.checkArgument(null != slaveDataSourceNames && !slaveDataSourceNames.isEmpty(), "SlaveDataSourceNames is required.");
        this.name = name;
        this.masterDataSourceName = masterDataSourceName;
        this.slaveDataSourceNames = slaveDataSourceNames;
        this.loadBalanceStrategyConfiguration = loadBalanceStrategyConfiguration;
    }
}
