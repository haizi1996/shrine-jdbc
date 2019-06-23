package com.hailin.shrine.sharding.jdbc.core.common.common.context.condition;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 表的字段
 */
@RequiredArgsConstructor
@Getter
@ToString
public final class Column {

    private final String name;

    private final String tableName;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        Column column = (Column) obj;
        return Objects.equal(this.name.toUpperCase(), column.name.toUpperCase()) && Objects.equal(this.tableName.toUpperCase(), column.tableName.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name.toUpperCase(), tableName.toUpperCase());
    }
}
