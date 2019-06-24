package com.hailin.shrine.sharding.jdbc.core.common.common.lexer;

import com.hailin.shrine.jdbc.core.common.constant.DatabaseType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


/**
 * 词法分析引擎的工厂
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LexerEngineFactory {

    /**
     * 创建一个词法分析引擎
     * @param dbType 数据库类型
     * @param sql 输入的sql语句
     */
    public static LexerEngine newInstance(final DatabaseType dbType , final String sql){
        switch (dbType) {
            case H2:
                return new LexerEngine(new H2Lexer(sql));
            case MySQL:
                return new LexerEngine(new MySQLLexer(sql));
            case Oracle:
                return new LexerEngine(new OracleLexer(sql));
            case SQLServer:
                return new LexerEngine(new SQLServerLexer(sql));
            case PostgreSQL:
                return new LexerEngine(new PostgreSQLLexer(sql));
            default:
                throw new UnsupportedOperationException(String.format("Cannot support database [%s].", dbType));
        }
    }
}
