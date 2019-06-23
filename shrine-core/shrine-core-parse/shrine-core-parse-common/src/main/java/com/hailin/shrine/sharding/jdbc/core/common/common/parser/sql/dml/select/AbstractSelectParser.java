package com.hailin.shrine.sharding.jdbc.core.common.common.parser.sql.dml.select;

import com.hailin.shrine.jdbc.core.common.metadate.table.ShardingTableMetaData;
import com.hailin.shrine.jdbc.core.common.rule.ShardingRule;
import com.hailin.shrine.sharding.jdbc.core.common.common.context.selectitem.SelectItem;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.LexerEngine;
import com.hailin.shrine.sharding.jdbc.core.common.common.parser.clause.facade.AbstractSelectClauseParserFacade;
import com.hailin.shrine.sharding.jdbc.core.common.common.parser.sql.SQLParser;
import com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement.SQLStatement;
import com.hailin.shrine.sharding.jdbc.core.common.sql.statement.dml.SelectStatement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * 选择语句的抽象解析器
 */
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractSelectParser implements SQLParser {

    //分片规则
    private final ShardingRule shardingRule;

    //词法引擎
    private final LexerEngine lexerEngine;

    //选择字句的门面
    private final AbstractSelectClauseParserFacade selectClauseParserFacade;

    private final List<SelectItem> items = new LinkedList<>();

    //分片表的元数据信息
    private final ShardingTableMetaData shardingTableMetaData;

    @Override
    public SelectStatement parse() {
        SelectStatement result = parseInternal();

        if (result.containsSubquery()) {
            result = result.mergeSubqueryStatement();
        }
        // TODO move to rewrite
        appendDerivedColumns(result);
        appendDerivedOrderBy(result);
        return result;
    }

    private SelectStatement parseInternal() {
        SelectStatement result = new SelectStatement();
        lexerEngine.nextToken();
        parseInternal(result);
        return result;
    }
}
