
package com.hailin.shrine.jdbc.core.common.strategy.route.none;


import com.hailin.shrine.jdbc.core.common.strategy.route.ShardingStrategy;
import com.hailin.shrine.jdbc.core.common.strategy.route.value.RouteValue;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * None sharding strategy.
 * 
 */
@Getter
public final class NoneShardingStrategy implements ShardingStrategy {
    
    private final Collection<String> shardingColumns = Collections.emptyList();
    
    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames, final Collection<RouteValue> shardingValues) {
        return availableTargetNames;
    }
}
