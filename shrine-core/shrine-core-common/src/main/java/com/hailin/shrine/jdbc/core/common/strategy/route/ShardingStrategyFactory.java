package com.hailin.shrine.jdbc.core.common.strategy.route;

import com.hailin.shrine.jdbc.api.config.sharding.strategy.ShardingStrategyConfiguration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 分配策略工厂
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShardingStrategyFactory {

    public static ShardingStrategy newInstance(final ShardingStrategyConfiguration shardingStrategyConfig) {
        if (shardingStrategyConfig instanceof StandardShardingStrategyConfiguration) {
            return new StandardShardingStrategy((StandardShardingStrategyConfiguration) shardingStrategyConfig);
        }
        if (shardingStrategyConfig instanceof InlineShardingStrategyConfiguration) {
            return new InlineShardingStrategy((InlineShardingStrategyConfiguration) shardingStrategyConfig);
        }
        if (shardingStrategyConfig instanceof ComplexShardingStrategyConfiguration) {
            return new ComplexShardingStrategy((ComplexShardingStrategyConfiguration) shardingStrategyConfig);
        }
        if (shardingStrategyConfig instanceof HintShardingStrategyConfiguration) {
            return new HintShardingStrategy((HintShardingStrategyConfiguration) shardingStrategyConfig);
        }
        return new NoneShardingStrategy();
    }
}
