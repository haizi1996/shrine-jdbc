
package com.hailin.shrine.jdbc.core.parse.common.lexer.analyzer;


import com.hailin.shrine.jdbc.core.parse.common.exception.ShardingException;

public final class UnterminatedCharException extends ShardingException {
    
    private static final long serialVersionUID = 8575890835166900925L;
    
    private static final String MESSAGE = "Illegal input, unterminated '%s'.";
    
    public UnterminatedCharException(final char terminatedChar) {
        super(String.format(MESSAGE, terminatedChar));
    }
    
    public UnterminatedCharException(final String terminatedChar) {
        super(String.format(MESSAGE, terminatedChar));
    }
}
