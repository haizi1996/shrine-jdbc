
package com.hailin.shrine.sharding.jdbc.core.common.common.exception;


import com.hailin.shrine.sharding.jdbc.core.common.common.lexer.token.TokenType;

/**
 * Throw exception when SQL not supported.
 * 
 */
public final class SQLParsingUnsupportedException extends ShardingException {
    
    private static final long serialVersionUID = -4968036951399076811L;
    
    private static final String MESSAGE = "Not supported token '%s'.";
    
    public SQLParsingUnsupportedException(final TokenType tokenType) {
        super(String.format(MESSAGE, tokenType.toString()));
    }

    public SQLParsingUnsupportedException(final String message) {
        super(message);
    }
}
