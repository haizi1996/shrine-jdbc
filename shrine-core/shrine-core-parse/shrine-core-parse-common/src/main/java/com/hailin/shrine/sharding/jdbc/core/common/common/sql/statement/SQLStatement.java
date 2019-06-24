package com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement;


import com.google.common.base.Optional;
import com.hailin.shrine.jdbc.core.common.constant.SQLType;
import com.hailin.shrine.sharding.jdbc.core.common.common.context.condition.Conditions;
import com.hailin.shrine.sharding.jdbc.core.common.common.context.table.Tables;
import com.hailin.shrine.sharding.jdbc.core.common.sql.token.SQLToken;

import java.util.List;

/**
 * SQL
 */
public interface SQLStatement {

    /**
     * 获取SQL语句的类型
     */
    SQLType getType();

    /**
     * Get tables.
     *
     * @return tables
     */
    Tables getTables();

    /**
     * 获取路由条件
     *
     */
    Conditions getRouteConditions();

    /**
     * Get encrypt conditions.
     *
     * @return conditions
     */
    Conditions getEncryptConditions();

    /**
     * Add SQL token.
     *
     * @param sqlToken SQL token
     */
    void addSQLToken(SQLToken sqlToken);

    /**
     * Find SQL token.
     *
     */
    <T extends SQLToken> Optional<T> findSQLToken(Class<T> sqlTokenType);

    /**
     * Get SQL tokens.
     *
     * @return SQL tokens
     */
    List<SQLToken> getSQLTokens();

    /**
     * Get index of parameters.
     *
     * @return index of parameters
     */
    int getParametersIndex();

    /**
     * Increase parameters index.
     */
    void increaseParametersIndex();

    /**
     * Get logic SQL.
     *
     */
    String getLogicSQL();

    /**
     * Set logic SQL.
     *
     */
    void setLogicSQL(String logicTable);

}
