package com.hailin.shrine.jdbc.core.common.rule;

import com.hailin.shrine.jdbc.api.config.sharding.masterslave.LoadBalanceStrategyConfiguration;
import com.hailin.shrine.jdbc.api.config.sharding.masterslave.MasterSlaveRuleConfiguration;
import com.hailin.shrine.jdbc.api.spi.masterslave.MasterSlaveLoadBalanceAlgorithm;
import com.hailin.shrine.jdbc.core.common.spi.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmServiceLoader;
import lombok.Getter;

import java.util.Collection;

/**
 * 数据库，表主从规则
 */
@Getter
public class MasterSlaveRule  {

    /**
     * 主从名
     */
    private final String name;

    //主数据源名
    private final String masterDataSourceName;

    //从库的数据源名
    private final Collection<String> slaveDataSourceNames;

    private final MasterSlaveLoadBalanceAlgorithm loadBalanceAlgorithm;

    private final MasterSlaveRuleConfiguration masterSlaveRuleConfiguration;

    public MasterSlaveRule(final String name, final String masterDataSourceName, final Collection<String> slaveDataSourceNames, final MasterSlaveLoadBalanceAlgorithm loadBalanceAlgorithm) {
        this.name = name;
        this.masterDataSourceName = masterDataSourceName;
        this.slaveDataSourceNames = slaveDataSourceNames;
        this.loadBalanceAlgorithm = null == loadBalanceAlgorithm ? new MasterSlaveLoadBalanceAlgorithmServiceLoader().newService() : loadBalanceAlgorithm;
        masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration(name, masterDataSourceName, slaveDataSourceNames,
                new LoadBalanceStrategyConfiguration(this.loadBalanceAlgorithm.getType(), this.loadBalanceAlgorithm.getProperties()));
    }

    public MasterSlaveRule(final MasterSlaveRuleConfiguration config) {
        name = config.getName();
        masterDataSourceName = config.getMasterDataSourceName();
        slaveDataSourceNames = config.getSlaveDataSourceNames();
        loadBalanceAlgorithm = createMasterSlaveLoadBalanceAlgorithm(config.getLoadBalanceStrategyConfiguration());
        masterSlaveRuleConfiguration = config;
    }

    private MasterSlaveLoadBalanceAlgorithm createMasterSlaveLoadBalanceAlgorithm(final LoadBalanceStrategyConfiguration loadBalanceStrategyConfiguration) {
        MasterSlaveLoadBalanceAlgorithmServiceLoader serviceLoader = new MasterSlaveLoadBalanceAlgorithmServiceLoader();
        return null == loadBalanceStrategyConfiguration ? serviceLoader.newService() : serviceLoader.newService(loadBalanceStrategyConfiguration.getType(), loadBalanceStrategyConfiguration.getProperties());
    }

    public boolean containDataSourceName(final String dataSourceName) {
        return masterDataSourceName.equals(dataSourceName) || slaveDataSourceNames.contains(dataSourceName);
    }
}
