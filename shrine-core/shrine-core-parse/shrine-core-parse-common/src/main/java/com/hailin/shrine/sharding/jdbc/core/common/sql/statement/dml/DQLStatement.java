package com.hailin.shrine.sharding.jdbc.core.common.sql.statement.dml;

import com.hailin.shrine.jdbc.core.common.constant.SQLType;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.DefaultKeyword;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.TokenType;
import com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement.AbstractSQLStatement;
import lombok.ToString;

@ToString(callSuper = true)
public class DQLStatement extends AbstractSQLStatement {

    public DQLStatement() {
        super(SQLType.DQL);
    }

    /**
     * 判断token是不是DQL
     * @param tokenType 传入的token
     */
    public static boolean isDQL(final TokenType tokenType) {
        return DefaultKeyword.SELECT == tokenType;
    }

}
