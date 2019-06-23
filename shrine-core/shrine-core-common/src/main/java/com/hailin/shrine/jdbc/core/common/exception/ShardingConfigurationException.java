
package com.hailin.shrine.jdbc.core.common.exception;

/**
 * 分片规则异常
 *
 */
public final class ShardingConfigurationException extends RuntimeException {
    
    private static final long serialVersionUID = -1360264079938958332L;
    

    public ShardingConfigurationException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }

    public ShardingConfigurationException(final Exception cause) {
        super(cause);
    }
}
