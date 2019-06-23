package com.hailin.shrine.jdbc.api.config;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;

import java.util.Properties;

/**
 * 基于SPI类型配置
 */
@Getter
public abstract class TypeBasedSPIConfiguration {
    //主键类型
    //#自增键的类型,主要用于调用内置的主键生成算法有三个可用值:
    // SNOWFLAKE(时间戳+worker id+自增id),
    // UUID(java.util.UUID类生成的随机UUID),
    // LEAF_SEGMENT
    private final String type;

    private final Properties properties;

    public TypeBasedSPIConfiguration(final String type) {
        this(type, null);
    }

    public TypeBasedSPIConfiguration(final String type, final Properties properties) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(type), "Type is required.");
        this.type = type;
        this.properties = null == properties ? new Properties() : properties;
    }
}
