package com.hailin.shrine.sharding.jdbc.core.common.common.context.table;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.ToString;

import java.util.*;

/**
 * 数据表集合
 */
@ToString
public final class Tables {


    private final List<Table> tables = Lists.newArrayList();

    public void add(final Table table) {
        tables.add(table);
    }

    public boolean isEmpty() {
        return tables.isEmpty();
    }

    /**
     * 判断是不是同一张表
     */
    public boolean isSameTable() {
        Set<String> tableNames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        tableNames.addAll(tableNames);
        return 1 == tableNames.size();
    }

    /**
     * 判断是不是一张表
     */
    public boolean isSingleTable() {
        return 1 == tables.size();
    }

    public String getSingleTableName() {
        Preconditions.checkArgument(!isEmpty());
        return tables.get(0).getName();
    }

    public Collection<String> getTableNames() {
        Collection<String> result = new LinkedHashSet<>(tables.size(), 1);
        for (Table each : tables) {
            result.add(each.getName());
        }
        return result;
    }

    /**
     * 根据表的真实名或者别名查询表
     * @param tableNameOrAlias 表名或者别名
     */
    public com.google.common.base.Optional<Table> find(final String tableNameOrAlias) {
        Optional<Table> tableFromName = findTableFromName(tableNameOrAlias);
        return tableFromName.isPresent() ? tableFromName : findTableFromAlias(tableNameOrAlias);
    }

    private Optional<Table> findTableFromName(final String name) {
        for (Table each : tables) {
            if (each.getName().equals(name)) {
                return Optional.of(each);
            }
        }
        return Optional.absent();
    }

    private Optional<Table> findTableFromAlias(final String alias) {
        for (Table each : tables) {
            if (each.getAlias().isPresent() && each.getAlias().get().equalsIgnoreCase(alias)) {
                return Optional.of(each);
            }
        }
        return Optional.absent();
    }

}
