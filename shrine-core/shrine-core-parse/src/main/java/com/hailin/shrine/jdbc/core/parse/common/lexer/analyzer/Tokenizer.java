package com.hailin.shrine.jdbc.core.parse.common.lexer.analyzer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Tokenizer {

    private static final int MYSQL_SPECIAL_COMMENT_BEGIN_SYMBOL_LENGTH = 1;
    /**
     * hint语法检查的开始长度
     */
    private static final int HINT_BEGIN_SYMBOL_LENGTH = 3;

    private static final int COMMENT_AND_HINT_END_SYMBOL_LENGTH = 2;

    /**
     * 注释开始匹配的长度
     */
    private static final int COMMENT_BEGIN_SYMBOL_LENGTH = 2;


    private final String input;

    private final Dictionary dictionary;

    private final int offset;

    /**
     * 跳过空格的
     * @return 返回下一个token的开始地址
     */
    public int skipWhiteSpace(){
        int length = 0;
        while (CharType.isWhitespace(charAt(offset + length))){
            length ++ ;
        }
        return offset + length;
    }
    private char charAt(final int index) {
        return index >= input.length() ? (char) CharType.EOI : input.charAt(index);
    }
    public int skipHint(){
        return untilCommentAndHintTerminateSign(HINT_BEGIN_SYMBOL_LENGTH);
    }

    private int untilCommentAndHintTerminateSign(int beginSymbolLength) {
        int length = beginSymbolLength;
        while (!isMultipleLineCommentEnd(charAt(offset + length), charAt(offset + length + 1))) {
            if (CharType.isEndOfInput(charAt(offset + length))){
                throw new UnterminatedCharException("*/");
            }
            length ++;
        }

        return offset + length + COMMENT_AND_HINT_END_SYMBOL_LENGTH;
    }

    /**
     * 是不是多行注释的结尾
     * @param ch 当前字符
     * @param next  下一个字符
     */
    private boolean isMultipleLineCommentEnd(final char ch , final  char next){
        return ch == '*' && next == '/';
    }

    /**
     * 跳过注释的字符
     */
    public int skipComment() {
        char current = charAt(offset);
        char next = charAt(offset+1);
        if(isSingleLineCommentBegin(current , next)){
            return skipSingleLineComment(COMMENT_BEGIN_SYMBOL_LENGTH);
        }else if ('#' == current){
            return skipSingleLineComment(MYSQL_SPECIAL_COMMENT_BEGIN_SYMBOL_LENGTH);
        }else if(isMultipleLineCommentBegin(current , next)){
            return skipMultipleLineComment();
        }
        return offset;
    }

    /**
     * 跳过多行注释
     */
    private int skipMultipleLineComment() {
        return untilCommentAndHintTerminateSign(COMMENT_BEGIN_SYMBOL_LENGTH);
    }

    /**
     * 是不是单行注释的开始
     */
    private boolean isMultipleLineCommentBegin(char current, char next) {
        return current == '/' && next == '*';
    }

    private int skipSingleLineComment(int commentBeginSymbolLength) {
        int length = commentBeginSymbolLength;
        while (!CharType.isEndOfInput(charAt(offset + length)) && '\n' != charAt(offset + length)){
            length ++;
        }
        return offset + length + 1;
    }

    /**
     * 是不是单行注释的开头
     */
    private boolean isSingleLineCommentBegin(char current, char next) {
        return (current == '-' && next == '-')
                ||(current == '/' && next == '/');
    }
}