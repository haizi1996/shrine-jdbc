package com.hailin.shrine.jdbc.api;

import java.util.Properties;

/**
 * 最基本算法的SPI
 */
public interface TypeBasedSPI {

    /**
     * 获取算法的类型
     */
    String getType();


    /**
     * Get properties.
     *
     */
    Properties getProperties();

    /**
     * Set properties.
     *
     */
    void setProperties(Properties properties);

}
