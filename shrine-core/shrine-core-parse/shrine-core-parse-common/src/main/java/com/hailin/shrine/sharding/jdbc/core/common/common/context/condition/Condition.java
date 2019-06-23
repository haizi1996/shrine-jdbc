package com.hailin.shrine.sharding.jdbc.core.common.common.context.condition;

import com.hailin.shrine.jdbc.core.common.constant.ShardingOperator;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * SQL条件
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class Condition {

    /**
     * 字段
     */
    private final Column column;

    private final ShardingOperator operator;

    @Setter
    private String compareOperator;

    private final Map<Integer, Comparable<?>> positionValueMap = new LinkedHashMap<>();

    private final Map<Integer, Integer> positionIndexMap = new LinkedHashMap<>();

    protected Condition() {
        column = null;
        operator = null;
    }

    public Condition(final Column column, final SQLExpression sqlExpression) {
        this(column, ShardingOperator.EQUAL);
        init(sqlExpression, 0);
    }
}
