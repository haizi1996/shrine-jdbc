package com.hailin.shrine.sharding.jdbc.core.common.common.context.limit;

import com.hailin.shrine.jdbc.core.common.constant.DatabaseType;
import com.hailin.shrine.jdbc.core.common.util.NumberUtil;
import com.hailin.shrine.sharding.jdbc.core.common.common.exception.SQLParsingException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public final class Limit {

    //偏移值
    private LimitValue offset;

    //行数值
    private LimitValue rowCount;


    /**
     * 获取偏移值
     */
    public int getOffsetValue() {
        return null != offset ? offset.getValue() : 0;
    }


    public int getRowCountValue() {
        return null != rowCount ? rowCount.getValue() : -1;
    }

    /**
     *  处理参数
     * @param parameters 参数集合
     * @param isFetchAll 是否获取所有数据
     * @param databaseType 数据库类型
     */
    public void processParameters(final List<Object> parameters, final boolean isFetchAll, final DatabaseType databaseType) {
        fill(parameters);
        rewrite(parameters, isFetchAll, databaseType);
    }

    private void rewrite(final List<Object> parameters, final boolean isFetchAll, final DatabaseType databaseType) {
        int rewriteOffset = 0;
        int rewriteRowCount;
        if (isFetchAll) {
            rewriteRowCount = Integer.MAX_VALUE;
        } else if (isNeedRewriteRowCount(databaseType)) {
            rewriteRowCount = null == rowCount ? -1 : getOffsetValue() + rowCount.getValue();
        } else {
            rewriteRowCount = rowCount.getValue();
        }
        if (null != offset && offset.getIndex() > -1) {
            parameters.set(offset.getIndex(), rewriteOffset);
        }
        if (null != rowCount && rowCount.getIndex() > -1) {
            parameters.set(rowCount.getIndex(), rewriteRowCount);
        }
    }
    /**
     * 填充参数
     */
    private void fill(List<Object> parameters) {
        int offset = 0;
        if (null != this.offset) {
            offset = -1 == this.offset.getIndex() ? getOffsetValue() : NumberUtil.roundHalfUp(parameters.get(this.offset.getIndex()));
            this.offset.setValue(offset);
        }
        int rowCount = 0;
        if (null != this.rowCount) {
            rowCount = -1 == this.rowCount.getIndex() ? getRowCountValue() : NumberUtil.roundHalfUp(parameters.get(this.rowCount.getIndex()));
            this.rowCount.setValue(rowCount);
        }
        if (offset < 0 || rowCount < 0) {
            throw new SQLParsingException("LIMIT offset and row count can not be a negative value.");
        }
    }

    /**
     * 判断是否需要重写行
     * @param databaseType 数据库类型
     */
    public boolean isNeedRewriteRowCount(final DatabaseType databaseType) {
        return DatabaseType.MySQL == databaseType || DatabaseType.PostgreSQL == databaseType || DatabaseType.H2 == databaseType;
    }
}
