package com.hailin.shrine.sharding.jdbc.core.common.common.parser.sql;

import com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement.SQLStatement;

/**
 * SQL解析的接口
 */
public interface SQLParser {


    /**
     * 解析SQL语句
     * @return
     */
    SQLStatement parse();
}
