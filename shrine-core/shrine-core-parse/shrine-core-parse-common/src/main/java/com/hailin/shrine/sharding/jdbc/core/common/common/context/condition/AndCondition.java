package com.hailin.shrine.sharding.jdbc.core.common.common.context.condition;

import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * And条件
 */
@Getter
@ToString
public class AndCondition {

    private final List<Condition> conditions = new LinkedList<>();


    public Map<Column, List<Condition>> getConditionsMap() {
        Map<Column, List<Condition>> result = new LinkedHashMap<>(conditions.size(), 1);
        for (Condition each : conditions) {
            if (!result.containsKey(each.getColumn())) {
                result.put(each.getColumn(), new LinkedList<Condition>());
            }
            result.get(each.getColumn()).add(each);
        }
        return result;
    }
    /**
     * 优化和调整
     */
    public AndCondition optimize() {
        AndCondition result = new AndCondition();
        for (Condition each : conditions) {
            if (Condition.class.equals(each.getClass())) {
                result.getConditions().add(each);
            }
        }
        if (result.getConditions().isEmpty()) {
            result.getConditions().add(new NullCondition());
        }
        return result;
    }
}
