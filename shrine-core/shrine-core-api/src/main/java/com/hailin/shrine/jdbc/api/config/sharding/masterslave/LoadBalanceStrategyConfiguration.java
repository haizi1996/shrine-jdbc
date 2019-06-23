package com.hailin.shrine.jdbc.api.config.sharding.masterslave;

import com.hailin.shrine.jdbc.api.config.TypeBasedSPIConfiguration;
import lombok.Getter;

import java.util.Properties;

/**
 * 主从负载均衡策略配置
 */
@Getter
public final class LoadBalanceStrategyConfiguration extends TypeBasedSPIConfiguration {
    public LoadBalanceStrategyConfiguration(final String type) {
        super(type);
    }

    public LoadBalanceStrategyConfiguration(final String type, final Properties properties) {
        super(type, properties);
    }
}
