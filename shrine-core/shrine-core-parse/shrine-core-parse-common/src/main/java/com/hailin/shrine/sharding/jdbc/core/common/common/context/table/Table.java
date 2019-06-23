package com.hailin.shrine.sharding.jdbc.core.common.common.context.table;

import com.google.common.base.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Table {

    @Getter
    private final String  name;

    private final String alias;

    /**
     * 获取别名.
     *
     */
    public Optional<String> getAlias() {
        return Optional.fromNullable(alias);
    }

}
