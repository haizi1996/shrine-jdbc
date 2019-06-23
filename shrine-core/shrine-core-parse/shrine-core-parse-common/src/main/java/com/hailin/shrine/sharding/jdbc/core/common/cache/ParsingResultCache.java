package com.hailin.shrine.sharding.jdbc.core.common.cache;

import com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement.SQLStatement;
import org.apache.commons.collections4.map.AbstractReferenceMap;
import org.apache.commons.collections4.map.ReferenceMap;

import java.util.Map;

/**
 * 解析结果的缓存
 */
public final class ParsingResultCache {

    private final Map<String, SQLStatement> cache = new ReferenceMap<>(AbstractReferenceMap.ReferenceStrength.SOFT, AbstractReferenceMap.ReferenceStrength.SOFT, 65535, 1);

    public void put(final String sql, final SQLStatement sqlStatement) {
        cache.put(sql, sqlStatement);
    }

    public SQLStatement getSQLStatement(final String sql) {
        return cache.get(sql);
    }

    public synchronized void clear() {
        cache.clear();
    }

}
