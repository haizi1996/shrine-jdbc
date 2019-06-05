package com.hailin.shrine.jdbc.core.parse.common.lexer;


import com.hailin.shrine.jdbc.core.parse.common.lexer.analyzer.CharType;
import com.hailin.shrine.jdbc.core.parse.common.lexer.analyzer.Dictionary;
import com.hailin.shrine.jdbc.core.parse.common.lexer.analyzer.Tokenizer;
import com.hailin.shrine.jdbc.core.parse.common.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Lexer {


    /**
     * 输入的sql语句
     */
    @Getter
    private final String input;


    private final Dictionary dictionary;

    /**
     * sql语句 偏移值
     */
    private int offset;

    @Getter
    private Token currentToken;

    /**
     *  分析下一个token
     */
    public void nextToken(){
        skipIgnoredToken();
    }

    /**
     * 跳过忽略的token
     */
    private void skipIgnoredToken() {
        offset = new Tokenizer(input ,dictionary , offset).skipWhiteSpace();
        while (isHintBegin()){
            offset = new Tokenizer(input , dictionary , offset).skipHint();
            offset = new Tokenizer(input , dictionary , offset).skipWhiteSpace();
        }
        while (isCommentBegin()){
            offset = new Tokenizer(input, dictionary, offset).skipComment();
            offset = new Tokenizer(input, dictionary, offset).skipWhiteSpace();
        }
    }

    /**
     * 是不是注释开头的标示
     */
    private boolean isCommentBegin() {
        char currentChar = getCurrentChar(0);
        char next = getCurrentChar(1);
        return ('/'==currentChar && '/' == next)
                || ('-' == currentChar && '-' == next)
                || ('/' == currentChar && '*' == next);
    }
    protected final char getCurrentChar(final int offset) {
        return this.offset + offset >= input.length() ? (char) CharType.EOI : input.charAt(this.offset + offset);
    }

    protected boolean isHintBegin(){
        return false;
    }



}