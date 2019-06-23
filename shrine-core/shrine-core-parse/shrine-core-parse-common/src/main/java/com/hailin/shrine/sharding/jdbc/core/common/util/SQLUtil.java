package com.hailin.shrine.sharding.jdbc.core.common.util;

import com.google.common.base.CharMatcher;
import com.hailin.shrine.jdbc.core.common.constant.DatabaseType;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.dialect.mysql.MySQLKeyword;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.DefaultKeyword;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * SQL工具类
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class SQLUtil {

    /**
     * 从sql表达式里面移除特殊字符
     * @param value sql表达式
     */
    public static String getExactlyValue(final String value) {
        return null == value ? null : CharMatcher.anyOf("[]`'\"").removeFrom(value);
    }

    /**
     * 从sql表达式里面移除空格字符
     * @param value SQL表达式
     */
    public static String getExactlyExpression(final String value) {
        return null == value ? null : CharMatcher.anyOf(" ").removeFrom(value);
    }

    /**
     * 获取原始值
     * @param value SQL表达式
     * @param databaseType 数据库类型
     */
    public static String getOriginalValue(final String value, final DatabaseType databaseType) {
        if (DatabaseType.MySQL != databaseType) {
            return value;
        }
        try {
            DefaultKeyword.valueOf(value.toUpperCase());
            return String.format("`%s`", value);
        } catch (final IllegalArgumentException ex) {
            return getOriginalValueForMySQLKeyword(value);
        }
    }

    private static String getOriginalValueForMySQLKeyword(final String value) {
        try {
            MySQLKeyword.valueOf(value.toUpperCase());
            return String.format("`%s`", value);
        } catch (final IllegalArgumentException ex) {
            return value;
        }
    }
}
