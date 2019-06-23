package com.hailin.shrine.jdbc.api.spi.keygen;

import com.hailin.shrine.jdbc.api.TypeBasedSPI;

public interface ShardingKeyGenerator extends TypeBasedSPI {

    /**
     * 获取主键
     *
     */
    Comparable<?> generateKey();
}
