package com.hailin.shrine.sharding.jdbc.core.common.common.dialect;

import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.LexerEngine;
import com.hailin.shrine.sharding.jdbc.core.common.common.parser.clause.expression.AliasExpressionParser;
import com.hailin.shrine.sharding.jdbc.core.common.common.parser.clause.expression.BasicExpressionParser;
import lombok.NoArgsConstructor;

/**
 * 表达式解析工厂
 */
@NoArgsConstructor
public final class ExpressionParserFactory {

    /**
     * 创建一个别名表达式解析接口
     * @param lexerEngine 词法引擎
     */
    public static AliasExpressionParser createAliasExpressionParser(final LexerEngine lexerEngine) {
        switch (lexerEngine.getDatabaseType()) {
            case H2:
            case MySQL:
                return new MySQLAliasExpressionParser(lexerEngine);
            case Oracle:
                return new OracleAliasExpressionParser(lexerEngine);
            case SQLServer:
                return new SQLServerAliasExpressionParser(lexerEngine);
            case PostgreSQL:
                return new PostgreSQLAliasExpressionParser(lexerEngine);
            default:
                throw new UnsupportedOperationException(String.format("Cannot support database type: %s", lexerEngine.getDatabaseType()));
        }
    }

    /**
     * 创建一个基本的表达式解析接口
     * @param lexerEngine 词法分析引擎
     */
    public static BasicExpressionParser createBasicExpressionParser(final LexerEngine lexerEngine) {
        return new BasicExpressionParser(lexerEngine);
    }
}
