package com.hailin.shrine.sharding.jdbc.core.common.sql.statement.dml;

import com.hailin.shrine.sharding.jdbc.core.common.common.context.condition.OrCondition;
import com.hailin.shrine.sharding.jdbc.core.common.common.context.selectitem.SelectItem;
import com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement.SQLStatement;
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

    private SelectStatement subqueryStatement;

    private Collection<SelectStatement> subqueryStatements = new LinkedList<>();

    private Collection<OrCondition> subqueryConditions = new LinkedList<>();
}
