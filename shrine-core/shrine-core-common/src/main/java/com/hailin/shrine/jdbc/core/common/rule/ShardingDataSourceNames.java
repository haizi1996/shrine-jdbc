package com.hailin.shrine.jdbc.core.common.rule;

import com.hailin.shrine.jdbc.api.config.sharding.ShardingRuleConfiguration;
import com.hailin.shrine.jdbc.api.config.sharding.masterslave.MasterSlaveRuleConfiguration;
import lombok.Getter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;

/**
 * 分片数据源命名
 * 会转换为实际主从数据源命名
 */
public final class ShardingDataSourceNames {

    //分片规则配置
    private final ShardingRuleConfiguration shardingRuleConfig;

    @Getter
    private final Collection<String> dataSourceNames;

    public ShardingDataSourceNames(final ShardingRuleConfiguration shardingRuleConfig, final Collection<String> rawDataSourceNames) {
        this.shardingRuleConfig = shardingRuleConfig;
        dataSourceNames = getAllDataSourceNames(rawDataSourceNames);
    }

    /**
     * 获取所有的数据源名称
     */
    private Collection<String> getAllDataSourceNames(final Collection<String> dataSourceNames) {
        Collection<String> result = new LinkedHashSet<>(dataSourceNames);
        for (MasterSlaveRuleConfiguration each : shardingRuleConfig.getMasterSlaveRuleConfigs()) {
            result.remove(each.getMasterDataSourceName());
            result.removeAll(each.getSlaveDataSourceNames());
            result.add(each.getName());
        }
        return result;
    }

    /**
     * 获取默认的数据源名
     */
    public String getDefaultDataSourceName() {
        return 1 == dataSourceNames.size() ? dataSourceNames.iterator().next() : shardingRuleConfig.getDefaultDataSourceName();
    }

    /**
     * 获取主库的数据源命名
     * @param dataSourceName
     * @return
     */
    public String getRawMasterDataSourceName(final String dataSourceName) {
        for (MasterSlaveRuleConfiguration each : shardingRuleConfig.getMasterSlaveRuleConfigs()) {
            if (each.getName().equals(dataSourceName)) {
                return each.getMasterDataSourceName();
            }
        }
        return dataSourceName;
    }

    /**
     * 获取一个随机的数据源名称
     *
     */
    public String getRandomDataSourceName() {
        return getRandomDataSourceName(dataSourceNames);
    }

    /**
     *获取一个随机的数据源名称
     *
     * @param dataSourceNames available data source names
     */
    public String getRandomDataSourceName(final Collection<String> dataSourceNames) {
        Random random = new Random();
        int index = random.nextInt(dataSourceNames.size());
        Iterator<String> iterator = dataSourceNames.iterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator.next();
    }
}
