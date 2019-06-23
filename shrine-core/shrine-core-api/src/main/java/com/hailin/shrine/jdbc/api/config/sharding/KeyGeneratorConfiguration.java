package com.hailin.shrine.jdbc.api.config.sharding;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hailin.shrine.jdbc.api.config.TypeBasedSPIConfiguration;
import lombok.Getter;

import java.util.Properties;

/**
 * 主键配置
 */
@Getter
public final class KeyGeneratorConfiguration  extends TypeBasedSPIConfiguration {

    //主键对应的类
    private final String column;

    public KeyGeneratorConfiguration(final String type, final String column) {
        super(type);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(column), "Column is required.");
        this.column = column;
    }

    public KeyGeneratorConfiguration(final String type, final String column, final Properties properties) {
        super(type, properties);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(column), "Column is required.");
        this.column = column;
    }
}
