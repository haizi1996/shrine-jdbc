package com.hailin.shrine.jdbc.core.parse.common.lexer.analyzer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CharType {

    /**
     * ascii码表 代替，默认是结束字符
     */
    public static final byte EOI = 0x1A;

    public static boolean isWhitespace(final char ch){
        return (ch <= 0x20 && EOI != ch) ||(ch >= 0x7F && ch <= 0xA0);
    }

    /**
     *
     * 是不是结束字符
     */
    public static boolean isEndOfInput(final char ch) {
        return ch == EOI;
    }

    /**
     * 该字符是否是字母
     */
    public static boolean isAlphabet(final char ch) {
        return ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z';
    }


    /**
     * 该字符是否是数字
     */
    public static boolean isDigital(final char ch) {
        return ch >= '0' && ch <= '9';
    }

    /**
     * 该字符是否是符号
     */
    public static boolean isSymbol(final char ch) {
        return '(' == ch || ')' == ch || '[' == ch || ']' == ch || '{' == ch || '}' == ch || '+' == ch || '-' == ch || '*' == ch || '/' == ch || '%' == ch || '^' == ch || '=' == ch
                || '>' == ch || '<' == ch || '~' == ch || '!' == ch || '?' == ch || '&' == ch || '|' == ch || '.' == ch || ':' == ch || '#' == ch || ',' == ch || ';' == ch;
    }
}
