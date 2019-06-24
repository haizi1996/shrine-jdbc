package com.hailin.shrine.sharding.jdbc.core.common.common.context.limit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 限制条件的值
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LimitValue {

    private int value;

    private int index;

    private boolean boundOpened;
}
