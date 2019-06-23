
package com.hailin.shrine.sharding.jdbc.core.common.common.exception;

public class ShardingException extends RuntimeException {
    
    private static final long serialVersionUID = -1343739516839252250L;
    

    public ShardingException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }
    

    public ShardingException(final Exception cause) {
        super(cause);
    }
}
