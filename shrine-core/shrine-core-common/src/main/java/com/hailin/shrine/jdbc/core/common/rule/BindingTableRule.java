package com.hailin.shrine.jdbc.core.common.rule;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hailin.shrine.jdbc.core.common.exception.ShardingConfigurationException;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public final class BindingTableRule {

    private final List<TableRule> tableRules;

    /**
     * 判断是否有该逻辑表
     * @param logicTableName 逻辑表名称
     */
    public boolean hasLogicTable(final String logicTableName) {
        for (TableRule each : tableRules) {
            if (each.getLogicTable().equals(logicTableName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deduce actual table name from other actual table name in same binding table rule.
     *
     * @param dataSource data source name
     * @param logicTable logic table name
     * @param otherActualTable other actual table name in same binding table rule
     * @return actual table name
     */
    public String getBindingActualTable(final String dataSource, final String logicTable, final String otherActualTable) {
        int index = -1;
        for (TableRule each : tableRules) {
            index = each.findActualTableIndex(dataSource, otherActualTable);
            if (-1 != index) {
                break;
            }
        }
        if (-1 == index) {
            throw new ShardingConfigurationException("Actual table [%s].[%s] is not in table config", dataSource, otherActualTable);
        }
        for (TableRule each : tableRules) {
            if (each.getLogicTable().equals(logicTable.toLowerCase())) {
                return each.getActualDataNodes().get(index).getTableName().toLowerCase();
            }
        }
        throw new ShardingConfigurationException("Cannot find binding actual table, data source: %s, logic table: %s, other actual table: %s", dataSource, logicTable, otherActualTable);
    }

    Collection<String> getAllLogicTables() {
        return Lists.transform(tableRules, new Function<TableRule, String>() {

            @Override
            public String apply(final TableRule input) {
                return input.getLogicTable().toLowerCase();
            }
        });
    }
}
