package com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement;


import com.google.common.base.Optional;
import com.hailin.shrine.jdbc.core.common.constant.SQLType;
import com.hailin.shrine.sharding.jdbc.core.common.common.context.condition.Conditions;
import com.hailin.shrine.sharding.jdbc.core.common.common.context.table.Tables;
import com.hailin.shrine.sharding.jdbc.core.common.sql.token.SQLToken;
import lombok.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter@ToString
public abstract class AbstractSQLStatement implements SQLStatement {

    //SQL类型
    private final SQLType type;

    //数据库表
    private final Tables tables = new Tables();

    //路由条件
    private final Conditions routeConditions = new Conditions();

    //编码条件 有的数据库表字段需要进行加密编码，比如密码字段
    private final Conditions encryptConditions = new Conditions();

    @Getter(AccessLevel.NONE)
    private final List<SQLToken> sqlTokens = new LinkedList<>();
    //参数的索引值
    private int parametersIndex;
    //sql语句
    private String logicSQL;

    @Override
    public final void addSQLToken(final SQLToken sqlToken) {
        sqlTokens.add(sqlToken);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T extends SQLToken> Optional<T> findSQLToken(final Class<T> sqlTokenType) {
        for (SQLToken each : sqlTokens) {
            if (each.getClass().equals(sqlTokenType)) {
                return Optional.of((T) each);
            }
        }
        return Optional.absent();
    }

    @Override
    public final List<SQLToken> getSQLTokens() {
        Collections.sort(sqlTokens);
        return sqlTokens;
    }

    @Override
    public final void increaseParametersIndex() {
        ++parametersIndex;
    }
}
