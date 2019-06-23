package com.hailin.shrine.jdbc.core.common.metadate;

import com.hailin.shrine.jdbc.core.common.metadate.table.TableMetaData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * 分片表的元数据信息
 */
@RequiredArgsConstructor
public final class ShardingMetaData {
    @Getter
    private final Map<String, TableMetaData> tables;

    public TableMetaData get(final String logicTableName) {
        return tables.get(logicTableName);
    }

    public void put(final String logicTableName, final TableMetaData tableMetaData) {
        tables.put(logicTableName, tableMetaData);
    }

    public void remove(final String logicTableName) {
        tables.remove(logicTableName);
    }

    public boolean containsTable(final String tableName) {
        return tables.containsKey(tableName);
    }

    public boolean containsColumn(final String tableName, final String column) {
        return containsTable(tableName) && tables.get(tableName).getColumns().keySet().contains(column.toLowerCase());
    }

    public Collection<String> getAllColumnNames(final String tableName) {
        return tables.containsKey(tableName) ? tables.get(tableName).getColumns().keySet() : Collections.<String>emptyList();
    }

}
