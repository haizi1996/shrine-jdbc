package com.hailin.shrine.sharding.jdbc.core.common.common.lexer;

import com.google.common.collect.Sets;
import com.hailin.shrine.jdbc.core.common.constant.DatabaseType;
import com.hailin.shrine.sharding.jdbc.core.common.common.exception.SQLParsingException;
import com.hailin.shrine.sharding.jdbc.core.common.common.exception.SQLParsingUnsupportedException;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.Assist;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.Symbol;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.Token;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.TokenType;
import com.hailin.shrine.sharding.jdbc.core.common.common.sql.statement.SQLStatement;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * 词法分析引擎
 */
@RequiredArgsConstructor
public final class LexerEngine {
    // 词法分析
    private final Lexer lexer;

    /**
     * 获取输入的字符串
     */
    public String getInput(){
        return lexer.getInput();
    }

    /**
     * 分析下一个token
     */
    public void nextToken() {
        lexer.nextToken();
    }

    /**
     *
     * 判断分析是否结束
     */
    public boolean isEnd() {
        return Assist.END == lexer.getCurrentToken().getType();
    }

    /**
     * 获取当前token
     *
     */
    public Token getCurrentToken() {
        return lexer.getCurrentToken();
    }

    /**
     * skip all tokens that inside parentheses.
     *
     * @param sqlStatement SQL statement
     * @return skipped string
     */
    public String skipParentheses(final SQLStatement sqlStatement) {
        StringBuilder result = new StringBuilder("");
        int count = 0;
        if (Symbol.LEFT_PAREN == lexer.getCurrentToken().getType()) {
            final int beginPosition = lexer.getCurrentToken().getEndPosition();
            result.append(Symbol.LEFT_PAREN.getLiterals());
            lexer.nextToken();
            while (true) {
                if (equalAny(Symbol.QUESTION)) {
                    sqlStatement.increaseParametersIndex();
                }
                if (Assist.END == lexer.getCurrentToken().getType() || (Symbol.RIGHT_PAREN == lexer.getCurrentToken().getType() && 0 == count)) {
                    break;
                }
                if (Symbol.LEFT_PAREN == lexer.getCurrentToken().getType()) {
                    count++;
                } else if (Symbol.RIGHT_PAREN == lexer.getCurrentToken().getType()) {
                    count--;
                }
                lexer.nextToken();
            }
            result.append(lexer.getInput().substring(beginPosition, lexer.getCurrentToken().getEndPosition()));
            lexer.nextToken();
        }
        return result.toString();
    }

    /**
     * Assert current token type should equals input token and go to next token type.
     *
     * @param tokenType token type
     */
    public void accept(final TokenType tokenType) {
        if (lexer.getCurrentToken().getType() != tokenType) {
            throw new SQLParsingException(lexer, tokenType);
        }
        lexer.nextToken();
    }

    /**
     * Judge current token equals one of input tokens or not.
     *
     * @param tokenTypes to be judged token types
     * @return current token equals one of input tokens or not
     */
    public boolean equalAny(final TokenType... tokenTypes) {
        for (TokenType each : tokenTypes) {
            if (each == lexer.getCurrentToken().getType()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Skip current token if equals one of input tokens.
     *
     * @param tokenTypes to be judged token types
     * @return skipped current token or not
     */
    public boolean skipIfEqual(final TokenType... tokenTypes) {
        if (equalAny(tokenTypes)) {
            lexer.nextToken();
            return true;
        }
        return false;
    }

    /**
     * Skip all input tokens.
     *
     * @param tokenTypes to be skipped token types
     */
    public void skipAll(final TokenType... tokenTypes) {
        Set<TokenType> tokenTypeSet = Sets.newHashSet(tokenTypes);
        while (tokenTypeSet.contains(lexer.getCurrentToken().getType())) {
            lexer.nextToken();
        }
    }

    /**
     * Skip until one of input tokens.
     *
     * @param tokenTypes to be skipped untiled token types
     */
    public void skipUntil(final TokenType... tokenTypes) {
        Set<TokenType> tokenTypeSet = Sets.newHashSet(tokenTypes);
        tokenTypeSet.add(Assist.END);
        while (!tokenTypeSet.contains(lexer.getCurrentToken().getType())) {
            lexer.nextToken();
        }
    }

    /**
     * Throw unsupported exception if current token equals one of input tokens.
     *
     * @param tokenTypes to be judged token types
     */
    public void unsupportedIfEqual(final TokenType... tokenTypes) {
        if (equalAny(tokenTypes)) {
            throw new SQLParsingUnsupportedException(lexer.getCurrentToken().getType());
        }
    }

    /**
     * Throw unsupported exception if current token not equals one of input tokens.
     *
     * @param tokenTypes to be judged token types
     */
    public void unsupportedIfNotSkip(final TokenType... tokenTypes) {
        if (!skipIfEqual(tokenTypes)) {
            throw new SQLParsingUnsupportedException(lexer.getCurrentToken().getType());
        }
    }

    /**
     * 获取数据库类型
     *
     */
    public DatabaseType getDatabaseType() {
        if (lexer instanceof H2Lexer) {
            return DatabaseType.H2;
        }
        if (lexer instanceof MySQLLexer) {
            return DatabaseType.MySQL;
        }
        if (lexer instanceof OracleLexer) {
            return DatabaseType.Oracle;
        }
        if (lexer instanceof SQLServerLexer) {
            return DatabaseType.SQLServer;
        }
        if (lexer instanceof PostgreSQLLexer) {
            return DatabaseType.PostgreSQL;
        }
        throw new UnsupportedOperationException(String.format("Cannot support lexer class: %s", lexer.getClass().getCanonicalName()));
    }
}
