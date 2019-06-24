package com.hailin.shrine.sharding.jdbc.core.common.common;

import com.google.common.base.Optional;
import com.hailin.shrine.jdbc.core.common.metadate.table.ShardingTableMetaData;
import com.hailin.shrine.sharding.jdbc.core.common.cache.ParsingResultCache;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.LexerEngine;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.LexerEngineFactory;
import com.hailin.shrine.sharding.jdbc.core.common.common.parser.sql.SQLParserFactory;
import com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement.SQLStatement;
import com.hailin.shrine.jdbc.core.common.constant.DatabaseType;
import com.hailin.shrine.jdbc.core.common.rule.ShardingRule;
import lombok.RequiredArgsConstructor;

/**
 * SQL解析引擎
 */
@RequiredArgsConstructor
public final class SQLParsingEngine {

    //数据库类型
    private final DatabaseType dbType;

    //SQL语句
    private final String sql;

    //分片规则
    private final ShardingRule shardingRule;

    //分片表的元数据
    private final ShardingTableMetaData shardingTableMetaData;

    //解析结果缓存
    private final ParsingResultCache parsingResultCache;

    /**
     * Parse SQL.
     *
     * @param useCache use cache or not
     */
    public SQLStatement parse(final boolean useCache) {
        Optional<SQLStatement> cachedSQLStatement = getSQLStatementFromCache(useCache);
        if (cachedSQLStatement.isPresent()) {
            return cachedSQLStatement.get();
        }
        LexerEngine lexerEngine = LexerEngineFactory.newInstance(dbType, sql);
        // 创建SQL解析器进行解析
        SQLStatement result = SQLParserFactory.newInstance(dbType, shardingRule, lexerEngine, shardingTableMetaData, sql).parse();
        if (useCache) {
            parsingResultCache.put(sql, result);
        }
        return result;
    }

    private Optional<SQLStatement> getSQLStatementFromCache(final boolean useCache) {
        return useCache ? Optional.fromNullable(parsingResultCache.getSQLStatement(sql)) : Optional.<SQLStatement>absent();
    }
}
