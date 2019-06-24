package com.hailin.shrine.sharding.jdbc.core.common.common.lexer;


import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.analyzer.CharType;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.analyzer.Dictionary;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.analyzer.Tokenizer;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 词法分析
 */
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

    /**
     * 开始字符是不是有效
     */
    protected boolean isVariableBegin() {
        return false;
    }

    /**
     * 是否支持空字符
     */
    protected boolean isSupportNChars() {
        return false;
    }

    /**
     * 开始字符是否是空字符
     */
    private boolean isNCharBegin() {
        return isSupportNChars() && 'N' == getCurrentChar(0) && '\'' == getCurrentChar(1);
    }

    private boolean isIdentifierBegin() {
        return isIdentifierBegin(getCurrentChar(0));
    }

    protected boolean isIdentifierBegin(final char ch) {
        return CharType.isAlphabet(ch) || '`' == ch || '_' == ch || '$' == ch;
    }

    private boolean isHexDecimalBegin() {
        return '0' == getCurrentChar(0) && 'x' == getCurrentChar(1);
    }

    private boolean isNumberBegin() {
        return CharType.isDigital(getCurrentChar(0)) || ('.' == getCurrentChar(0) && CharType.isDigital(getCurrentChar(1)) && !isIdentifierBegin(getCurrentChar(-1))
                || ('-' == getCurrentChar(0) && ('.' == getCurrentChar(1) || CharType.isDigital(getCurrentChar(1)))));
    }

    private boolean isSymbolBegin() {
        return CharType.isSymbol(getCurrentChar(0));
    }

    protected boolean isCharsBegin() {
        return '\'' == getCurrentChar(0) || '\"' == getCurrentChar(0);
    }

    private boolean isEnd() {
        return offset >= input.length();
    }
}
