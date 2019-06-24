package com.hailin.shrine.sharding.jdbc.core.common.sql.statement.dml;

import com.hailin.shrine.sharding.jdbc.core.common.common.context.condition.OrCondition;
import com.hailin.shrine.sharding.jdbc.core.common.common.context.limit.Limit;
import com.hailin.shrine.sharding.jdbc.core.common.common.context.orderby.OrderItem;
import com.hailin.shrine.sharding.jdbc.core.common.common.context.selectitem.SelectItem;
import com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement.SQLStatement;
import com.hailin.shrine.sharding.jdbc.core.common.common.sql.token.OffsetToken;
import com.hailin.shrine.sharding.jdbc.core.common.common.sql.token.RowCountToken;
import com.hailin.shrine.sharding.jdbc.core.common.sql.token.SQLToken;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;


@Getter
@Setter
@ToString(callSuper = true ,  exclude = "parentStatement")
public final class SelectStatement implements SQLStatement {

    private boolean containStar;

    private int firstSelectItemStartIndex;

    private int selectListStopIndex;

    private int groupByLastIndex;

    private final Set<SelectItem> items = new LinkedHashSet<>();

    private final List<OrderItem> groupByItems = new LinkedList<>();

    private final List<OrderItem> orderByItems = new LinkedList<>();

    private Limit limit;

    private SelectStatement parentStatement;

    //子查询
    private SelectStatement subqueryStatement;

    private Collection<SelectStatement> subqueryStatements = new LinkedList<>();

    private Collection<OrCondition> subqueryConditions = new LinkedList<>();

    /**
     * 判断是否是子查询
     */
    public boolean containsSubquery() {
        return null != subqueryStatement;
    }

    /**
     * 如果有子查询，就meger子查询
     */
    public SelectStatement mergeSubqueryStatement() {
        //处理子查询的限制limit条件
        SelectStatement result = processLimitForSubquery();
        processItems(result);
        processOrderByItems(result);
        result.setParametersIndex(getParametersIndex());
        return result;
    }

    /**
     * 处理子查询的limit条件
     */
    private SelectStatement processLimitForSubquery() {
        SelectStatement result = this;
        //获取所有的limit token
        List<SQLToken> limitSQLTokens = getLimitTokens(result);
        Limit limit = result.getLimit();
        while (result.containsSubquery()) {
            result = result.subqueryStatement;
            limitSQLTokens.addAll(getLimitTokens(result));
            if (null == result.getLimit()) {
                continue;
            }
            if (null == limit) {
                limit = result.getLimit();
            }
            if (null != result.getLimit().getRowCount()) {
                limit.setRowCount(result.getLimit().getRowCount());
            }
            if (null != result.getLimit().getOffset()) {
                limit.setOffset(result.getLimit().getOffset());
            }
        }
        resetLimitTokens(result, limitSQLTokens);
        result.setLimit(limit);
        return result;
    }

    /**
     * 重置limit的token,移除selectStatement的limit token，使用传入的limit token
     * @param selectStatement select单元
     * @param limitSQLTokens limit的token
     */
    private void resetLimitTokens(final SelectStatement selectStatement, final List<SQLToken> limitSQLTokens) {
        List<SQLToken> sqlTokens = selectStatement.getSQLTokens();
        Iterator<SQLToken> sqlTokenIterator = sqlTokens.iterator();
        while (sqlTokenIterator.hasNext()) {
            SQLToken each = sqlTokenIterator.next();
            if (each instanceof RowCountToken || each instanceof OffsetToken) {
                sqlTokenIterator.remove();
            }
        }
        sqlTokens.addAll(limitSQLTokens);
    }

    /**
     * 获取所有的limit token
     */
    private List<SQLToken> getLimitTokens(final SelectStatement selectStatement) {
        List<SQLToken> result = new LinkedList<>();
        for (SQLToken each : selectStatement.getSQLTokens()) {
            if (each instanceof RowCountToken || each instanceof OffsetToken) {
                result.add(each);
            }
        }
        return result;
    }
}
