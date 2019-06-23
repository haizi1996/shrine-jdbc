package com.hailin.shrine.jdbc.core.common.rule;

import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.hailin.shrine.jdbc.core.common.exception.ShardingConfigurationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 分片数据单元
 */
@RequiredArgsConstructor
@Getter
@ToString
public final class DataNode {

    private static final String DELIMITER = ".";
    //数据源名称
    private final String dataSourceName;
    //数据库表名
    private final String tableName;

    public DataNode(final String dataNode) {
        if (!isValidDataNode(dataNode)) {
            throw new ShardingConfigurationException("Invalid format for actual data nodes: '%s'", dataNode);
        }
        List<String> segments = Splitter.on(DELIMITER).splitToList(dataNode);
        dataSourceName = segments.get(0);
        tableName = segments.get(1);
    }

    private static boolean isValidDataNode(final String dataNodeStr) {
        return dataNodeStr.contains(DELIMITER) && 2 == Splitter.on(DELIMITER).splitToList(dataNodeStr).size();
    }
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (null == object || getClass() != object.getClass()) {
            return false;
        }
        DataNode dataNode = (DataNode) object;
        return Objects.equal(this.dataSourceName.toUpperCase(), dataNode.dataSourceName.toUpperCase())
                && Objects.equal(this.tableName.toUpperCase(), dataNode.tableName.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dataSourceName.toUpperCase(), tableName.toUpperCase());
    }
}
