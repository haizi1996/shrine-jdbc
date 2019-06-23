package com.hailin.shrine.sharding.jdbc.core.common.common.parser.sql;

import com.hailin.shrine.sharding.jdbc.core.common.common.exception.SQLParsingUnsupportedException;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.LexerEngine;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.TokenType;
import com.hailin.shrine.jdbc.core.common.metadate.table.ShardingTableMetaData;
import com.hailin.shrine.jdbc.core.common.constant.DatabaseType;
import com.hailin.shrine.jdbc.core.common.rule.ShardingRule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SQL解析工厂
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SQLParserFactory {

    /**
     * 创建SQL解析器
     * @param dbType 数据库类型
     * @param shardingRule 分片规则
     * @param lexerEngine 词法引擎
     * @param shardingTableMetaData 分片表元数据信息
     * @param sql sql语句
     */
    public static SQLParser newInstance(
            final DatabaseType dbType, final ShardingRule shardingRule, final LexerEngine lexerEngine, final ShardingTableMetaData shardingTableMetaData, final String sql) {
        lexerEngine.nextToken();
        //当前token的类型
        TokenType tokenType = lexerEngine.getCurrentToken().getType();
        if ()
        throw new SQLParsingUnsupportedException(tokenType);
    }

}
