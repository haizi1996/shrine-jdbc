package com.hailin.shrine.jdbc.core.common.metadate.table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  数据库表的元数据
 */
@Getter
@EqualsAndHashCode
@ToString
public final class TableMetaData {

    //key 为字段名
    private final Map<String, ColumnMetaData> columns;

    public TableMetaData(final Collection<ColumnMetaData> columnMetaDataList) {
        columns = new LinkedHashMap<>(columnMetaDataList.size(), 1);
        for (ColumnMetaData each : columnMetaDataList) {
            columns.put(each.getColumnName(), each);
        }
    }
}
