package com.hailin.shrine.sharding.jdbc.core.common.common.context.condition;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.hailin.shrine.sharding.jdbc.core.common.common.parser.clause.condition.NullCondition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 逻辑或条件
 */
@NoArgsConstructor
@Getter
@ToString
public final class OrCondition {


    private final List<AndCondition> andConditions = new ArrayList<>();

    public OrCondition(final Condition condition) {
        add(condition);
    }

    public void add(final Condition condition) {
        if (andConditions.isEmpty()) {
            andConditions.add(new AndCondition());
        }
        andConditions.get(0).getConditions().add(condition);
    }

    public OrCondition optimize() {
        for (AndCondition each : andConditions) {
            if (each.getConditions().get(0) instanceof NullCondition) {
                OrCondition result = new OrCondition();
                result.add(new NullCondition());
                return result;
            }
        }
        return this;
    }

    public List<Condition> findConditions(final Column column) {
        List<Condition> result = new LinkedList<>();
        for (AndCondition each : andConditions) {
            result.addAll(Collections2.filter(each.getConditions(), new Predicate<Condition>() {

                @Override
                public boolean apply(final Condition input) {
                    return input.getColumn().equals(column);
                }
            }));
        }
        return result;
    }
}
