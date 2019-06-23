package com.hailin.shrine.jdbc.api.config.sharding;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hailin.shrine.jdbc.api.config.sharding.strategy.ShardingStrategyConfiguration;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class TableRuleConfiguration {

    //逻辑表
    private final String logicTable;

    //实际数据
    private  final String actualDataNodes;

    //逻辑索引
    private String logicIndex;

    //数据库分片策略配置
    private ShardingStrategyConfiguration databaseShardingStrategyConfig;

    //数据表分片策略配置
    private ShardingStrategyConfiguration tableShardingStrategyConfig;

    //注解配置
    private KeyGeneratorConfiguration keyGeneratorConfig;

    public TableRuleConfiguration(final String logicTable) {
        this(logicTable, null);
    }

    public TableRuleConfiguration(final String logicTable, final String actualDataNodes) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(logicTable), "LogicTable is required.");
        this.logicTable = logicTable;
        this.actualDataNodes = actualDataNodes;
    }
}
