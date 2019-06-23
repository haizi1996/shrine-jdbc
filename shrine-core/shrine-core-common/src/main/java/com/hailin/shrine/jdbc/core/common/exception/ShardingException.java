
package com.hailin.shrine.jdbc.core.common.exception;

/**
 * Sharding exception.
 * 
 */
public class ShardingException extends RuntimeException {
    
    private static final long serialVersionUID = -1343739516839252250L;
    

    public ShardingException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }
    

    public ShardingException(final String message, final Exception cause) {
        super(message, cause);
    }
    

    public ShardingException(final Exception cause) {
        super(cause);
    }
}
