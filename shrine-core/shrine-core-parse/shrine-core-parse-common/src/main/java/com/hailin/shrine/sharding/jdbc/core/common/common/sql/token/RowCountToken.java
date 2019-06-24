package com.hailin.shrine.sharding.jdbc.core.common.common.sql.token;

import com.hailin.shrine.sharding.jdbc.core.common.sql.token.SQLToken;
import lombok.Getter;

/**
 * 行数  limit条件
 */
@Getter
public final class RowCountToken extends SQLToken {

    private final int rowCount;

    public RowCountToken(final int startIndex, final int rowCount) {
        super(startIndex);
        this.rowCount = rowCount;
    }
}
