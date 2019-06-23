package com.hailin.shrine.jdbc.core.common.constant;

/**
 * SQL类型
 */

public enum SQLType {

    /**
     * 数据库查询语句
     * SELECT
     */
    DQL,

    /**
     * 数据操作语句
     *  INSERT ， UPDATE ， DELETE
     */
    DML,


    /**
     * 数据定义语句
     * CREATE ， ALTER ， TRUNCATE
     */
    DDL,

    /**
     * 事务控制语句
     * SET ， COMMIT ， ROLLBACK ， SAVEPOIINT ， BEGIN
     */
    TCL,

    /**
     * 数据库管理语句
     */
    DAL,

    /**
     * 数据库控制语句
     */
    DCL

    /**
     * 数据库通用语言
     */
    ,GENERAL
}
