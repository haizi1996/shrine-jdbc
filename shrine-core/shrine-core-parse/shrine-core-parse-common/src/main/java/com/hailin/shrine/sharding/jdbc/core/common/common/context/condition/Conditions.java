package com.hailin.shrine.sharding.jdbc.core.common.common.context.condition;

import lombok.Getter;
import lombok.ToString;

/**
 * 条件集合
 */
@Getter
@ToString
public final class Conditions {
    private final OrCondition orCondition = new OrCondition();

    public void add(final Condition condition) {
        // TODO self-join has problem, table name maybe use alias
        orCondition.add(condition);
    }
}
