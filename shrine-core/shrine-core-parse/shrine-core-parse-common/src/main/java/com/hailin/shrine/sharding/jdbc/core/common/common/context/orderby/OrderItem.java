package com.hailin.shrine.sharding.jdbc.core.common.common.context.orderby;

import com.google.common.base.Optional;
import com.hailin.shrine.jdbc.core.common.constant.OrderDirection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *  排序项
 */
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderItem {

    /**
     * 所属的表名
     */
    private final String owner;
    /**
     * 字段名称
     */
    private final String name;

    /**
     * 排序的方向
     */
    private final OrderDirection orderDirection;

    private final OrderDirection nullOrderDirection;

    private int index = -1;

    /**
     * 表达式
     */
    private String expression;

    /**
     * 字段别名
     */
    private String alias;

    public OrderItem(final String name, final OrderDirection orderDirection, final OrderDirection nullOrderDirection) {
        this(null, name, orderDirection, nullOrderDirection);
    }

    public OrderItem(final int index, final OrderDirection orderDirection, final OrderDirection nullOrderDirection) {
        this(null, null, orderDirection, nullOrderDirection);
        this.index = index;
    }

    /**
     * Get owner.
     *
     * @return owner
     */
    public Optional<String> getOwner() {
        return Optional.fromNullable(owner);
    }

    /**
     * Get name.
     *
     * @return name
     */
    public Optional<String> getName() {
        return Optional.fromNullable(name);
    }

    /**
     * Get alias.
     *
     * @return alias
     */
    public Optional<String> getAlias() {
        return Optional.fromNullable(alias);
    }

    /**
     * Get column label.
     *
     * @return column label
     */
    public String getColumnLabel() {
        return null == alias ? name : alias;
    }

    /**
     * Get qualified name.
     *
     * @return qualified name
     */
    public Optional<String> getQualifiedName() {
        if (null == name) {
            return Optional.absent();
        }
        return null == owner ? Optional.of(name) : Optional.of(owner + "." + name);
    }

    /**
     * 判断排序项是否是索引
     * @return
     */
    public boolean isIndex() {
        return -1 != index;
    }


    @Override
    public boolean equals(final Object obj) {
        if (null == obj || !(obj instanceof OrderItem)) {
            return false;
        }
        OrderItem orderItem = (OrderItem) obj;
        return orderDirection == orderItem.getOrderDirection() && (columnLabelEquals(orderItem) || qualifiedNameEquals(orderItem) || indexEquals(orderItem));
    }

    private boolean columnLabelEquals(final OrderItem orderItem) {
        String columnLabel = getColumnLabel();
        return null != columnLabel && columnLabel.equalsIgnoreCase(orderItem.getColumnLabel());
    }

    private boolean qualifiedNameEquals(final OrderItem orderItem) {
        Optional<String> thisQualifiedName = getQualifiedName();
        Optional<String> thatQualifiedName = orderItem.getQualifiedName();
        return thisQualifiedName.isPresent() && thatQualifiedName.isPresent() && thisQualifiedName.get().equalsIgnoreCase(thatQualifiedName.get());
    }

    private boolean indexEquals(final OrderItem orderItem) {
        return -1 != index && index == orderItem.getIndex();
    }

}
