package com.hailin.shrine.jdbc.core.common.rule;

import com.google.common.base.Preconditions;
import com.hailin.shrine.jdbc.api.config.sharding.ShardingRuleConfiguration;
import com.hailin.shrine.jdbc.api.spi.keygen.ShardingKeyGenerator;
import com.hailin.shrine.jdbc.core.common.strategy.route.ShardingStrategy;
import lombok.Getter;

import java.util.Collection;

/**
 * 数据库和表的分片规则
 */
@Getter
public class ShardingRule implements BaseRule {

    /**
     * 分片规则配置
     */
    private final ShardingRuleConfiguration shardingRuleConfig;

    private final ShardingDataSourceNames shardingDataSourceNames;

    private final Collection<TableRule> tableRules;

    private final Collection<BindingTableRule> bindingTableRules;

    private final Collection<String> broadcastTables;

    private final ShardingStrategy defaultDatabaseShardingStrategy;

    private final ShardingStrategy defaultTableShardingStrategy;

    private final ShardingKeyGenerator defaultShardingKeyGenerator;

    private final Collection<MasterSlaveRule> masterSlaveRules;

    private final ShardingEncryptorEngine shardingEncryptorEngine;

    public ShardingRule(final ShardingRuleConfiguration shardingRuleConfig, final Collection<String> dataSourceNames) {
        Preconditions.checkArgument(!dataSourceNames.isEmpty(), "Data sources cannot be empty.");
        this.shardingRuleConfig = shardingRuleConfig;
        shardingDataSourceNames = new ShardingDataSourceNames(shardingRuleConfig, dataSourceNames);
        tableRules = createTableRules(shardingRuleConfig);
        bindingTableRules = createBindingTableRules(shardingRuleConfig.getBindingTableGroups());
        broadcastTables = shardingRuleConfig.getBroadcastTables();
        defaultDatabaseShardingStrategy = createDefaultShardingStrategy(shardingRuleConfig.getDefaultDatabaseShardingStrategyConfig());
        defaultTableShardingStrategy = createDefaultShardingStrategy(shardingRuleConfig.getDefaultTableShardingStrategyConfig());
        defaultShardingKeyGenerator = createDefaultKeyGenerator(shardingRuleConfig.getDefaultKeyGeneratorConfig());
        masterSlaveRules = createMasterSlaveRules(shardingRuleConfig.getMasterSlaveRuleConfigs());
        shardingEncryptorEngine = createShardingEncryptorEngine(shardingRuleConfig.getEncryptRuleConfig());
    }
}
