package com.hailin.shrine.jdbc.core.parse.common.lexer.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class Token {

    private final TokenType type;

    private final String literals;

    /**
     * sql语句当前token
     * 结束的索引值
     */
    private final int endPosition;

}
