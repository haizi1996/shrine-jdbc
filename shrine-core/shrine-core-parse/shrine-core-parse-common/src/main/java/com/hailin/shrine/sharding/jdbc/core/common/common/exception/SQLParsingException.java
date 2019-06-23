
package com.hailin.shrine.sharding.jdbc.core.common.common.exception;


import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.Lexer;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.LexerEngine;
import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.TokenType;

/**
 * Throw exception when SQL parsing error.
 * 
 */
public final class SQLParsingException extends ShardingException {
    
    private static final long serialVersionUID = -6408790652103666096L;
    
    private static final String UNMATCH_MESSAGE = "SQL syntax error, expected token is '%s', actual token is '%s', literals is '%s'.";
    
    private static final String TOKEN_ERROR_MESSAGE = "SQL syntax error, token is '%s', literals is '%s'.";
    
    public SQLParsingException(final String message, final Object... args) {
        super(message, args);
    }
    
    public SQLParsingException(final Lexer lexer, final TokenType expectedTokenType) {
        super(String.format(UNMATCH_MESSAGE, expectedTokenType, lexer.getCurrentToken().getType(), lexer.getCurrentToken().getLiterals()));
    }
    
    public SQLParsingException(final LexerEngine lexerEngine) {
        super(String.format(TOKEN_ERROR_MESSAGE, lexerEngine.getCurrentToken().getType(), lexerEngine.getCurrentToken().getLiterals()));
    }
}
