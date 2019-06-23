package com.hailin.shrine.jdbc.api.config.sharding;

import com.google.common.collect.Lists;
import com.hailin.shrine.jdbc.api.config.RuleConfiguration;
import com.hailin.shrine.jdbc.api.config.encryptor.EncryptRuleConfiguration;
import com.hailin.shrine.jdbc.api.config.sharding.masterslave.MasterSlaveRuleConfiguration;
import com.hailin.shrine.jdbc.api.config.sharding.strategy.ShardingStrategyConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 分片规则配置
 */
@Getter
@Setter
public class ShardingRuleConfiguration implements RuleConfiguration {
    //数据表规则配置集合
    private Collection<TableRuleConfiguration> tableRuleConfigurations = Lists.newLinkedList();

    //关联表规则
    private Collection<String> bindingTableGroups = new LinkedList<>();

    //广播数据表
    private Collection<String> broadcastTables = new LinkedList<>();

    //默认数据源名
    private String defaultDataSourceName;

    private ShardingStrategyConfiguration defaultDatabaseShardingStrategyConfig;

    private ShardingStrategyConfiguration defaultTableShardingStrategyConfig;

    //默认主键配置
    private KeyGeneratorConfiguration defaultKeyGeneratorConfig;

    //主从规则配置集合
    private Collection<MasterSlaveRuleConfiguration> masterSlaveRuleConfigs = new LinkedList<>();

    //加密配置
    private EncryptRuleConfiguration encryptRuleConfig;

}
