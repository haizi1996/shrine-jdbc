package com.hailin.shrine.sharding.jdbc.core.common.sql.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@RequiredArgsConstructor
@Getter
@ToString
public abstract class SQLToken implements Comparable<SQLToken> {
    private final int startIndex;

    @Override
    public final int compareTo(final SQLToken sqlToken) {
        return startIndex - sqlToken.getStartIndex();
    }

}
