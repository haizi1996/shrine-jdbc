package com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Token {

    //类型
    private final TokenType type;

    //词法字面量标记
    private final String literals;

    //literals 在 SQL 里的结束位置
    private final  int endPosition;

}
