package com.hailin.shrine.jdbc.core.common.strategy.route;

import com.hailin.shrine.jdbc.core.common.strategy.route.value.RouteValue;

import java.util.Collection;

/**
 * 分片策略接口
 */
public interface ShardingStrategy {

    /**
     * 获取分片列的集合
     */
    Collection<String> getShardingColumns();

    /**
     * 获取这个分片值在可用数据源中结果
     * @param availableTargetNames 可用的数据源名称或者数据库表名
     * @param shardingValues 分片的值
     */
    Collection<String> doSharding(Collection<String> availableTargetNames, Collection<RouteValue> shardingValues);

}
