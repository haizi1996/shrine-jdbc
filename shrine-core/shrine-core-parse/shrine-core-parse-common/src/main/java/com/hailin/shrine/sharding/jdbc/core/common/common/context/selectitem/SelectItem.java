package com.hailin.shrine.sharding.jdbc.core.common.common.context.selectitem;

import com.google.common.base.Optional;

/**
 * select语句的select 项
 */
public interface SelectItem {

    /**
     * 获取表达式
     */
    String getExpression();

    /**
     * 获取别名.
     *
     */
    Optional<String> getAlias();
}
