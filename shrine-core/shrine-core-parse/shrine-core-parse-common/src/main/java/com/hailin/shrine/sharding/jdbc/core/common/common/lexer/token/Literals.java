package com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token;

/**
 * 词法字面量标记
 */
public enum Literals implements TokenType{
    //整数
    INT,
    //浮点数
    FLOAT,
    //16进制
    HEX,
    //字符串
    CHARS,
    //词法关键字
    IDENTIFIER,
    //变量
    VARIABLE
}
