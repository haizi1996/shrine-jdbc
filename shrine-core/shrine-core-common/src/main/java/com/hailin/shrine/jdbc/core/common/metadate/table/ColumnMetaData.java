package com.hailin.shrine.jdbc.core.common.metadate.table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 数据库字段的元数据信息
 */
@RequiredArgsConstructor
@Getter@EqualsAndHashCode
@ToString
public class ColumnMetaData {

    //字段名
    private final String columnName;

    //数据类型
    private final String dataType;

    //是否是主键
    private final boolean primaryKey;
}
