package com.hailin.shrine.sharding.jdbc.core.common.common.parser.clause;

import com.hailin.shrine.jdbc.core.common.rule.ShardingRule;
import com.hailin.shrine.sharding.jdbc.core.common.common.dialect.ExpressionParserFactory;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.LexerEngine;
import com.hailin.shrine.sharding.jdbc.core.common.common.parser.clause.expression.AliasExpressionParser;
import lombok.Getter;

/**
 * 选择列表 字句解析
 */
@Getter
public abstract class SelectListClauseParser implements SQLClauseParser {

    //分片规则
    private final ShardingRule shardingRule;

    //词法引擎
    private final LexerEngine lexerEngine;

    //别名表达式解析
    private final AliasExpressionParser aliasExpressionParser;


    public SelectListClauseParser(final ShardingRule shardingRule, final LexerEngine lexerEngine) {
        this.shardingRule = shardingRule;
        this.lexerEngine = lexerEngine;
        aliasExpressionParser = ExpressionParserFactory.createAliasExpressionParser(lexerEngine);
    }
}
