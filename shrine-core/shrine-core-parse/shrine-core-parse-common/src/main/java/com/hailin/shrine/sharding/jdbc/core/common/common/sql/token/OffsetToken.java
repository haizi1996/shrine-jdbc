package com.hailin.shrine.sharding.jdbc.core.common.common.sql.token;

import com.hailin.shrine.sharding.jdbc.core.common.sql.token.SQLToken;

/**
 * limit 偏移的 tokn
 */
public final class OffsetToken extends SQLToken {

    private final int offset;

    public OffsetToken(final int startIndex, final int offset) {
        super(startIndex);
        this.offset = offset;
    }
}
