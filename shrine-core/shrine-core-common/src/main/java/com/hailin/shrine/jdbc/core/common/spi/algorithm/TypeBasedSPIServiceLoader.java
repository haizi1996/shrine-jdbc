package com.hailin.shrine.jdbc.core.common.spi.algorithm;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.hailin.shrine.jdbc.api.TypeBasedSPI;
import com.hailin.shrine.jdbc.core.common.exception.ShardingConfigurationException;
import com.hailin.shrine.jdbc.core.common.spi.NewInstanceServiceLoader;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Properties;

@RequiredArgsConstructor
public class TypeBasedSPIServiceLoader<T extends TypeBasedSPI> {

    private final Class<T> classType;

    /**
     * Create new instance for type based SPI.
     *
     * @param type SPI type
     * @param props SPI properties
     * @return SPI instance
     */
    public final T newService(final String type, final Properties props) {
        Collection<T> typeBasedServices = loadTypeBasedServices(type);
        if (typeBasedServices.isEmpty()) {
            throw new ShardingConfigurationException("Invalid `%s` SPI type `%s`.", classType.getName(), type);
        }
        T result = typeBasedServices.iterator().next();
        result.setProperties(props);
        return result;
    }


    /**
     * Create new service by default SPI type.
     *
     * @return type based SPI instance
     */
    public final T newService() {
        T result = loadFirstTypeBasedService();
        result.setProperties(new Properties());
        return result;
    }


    private Collection<T> loadTypeBasedServices(final String type) {
        return Collections2.filter(NewInstanceServiceLoader.newServiceInstances(classType), new Predicate<T>() {

            @Override
            public boolean apply(final T input) {
                return type.equalsIgnoreCase(input.getType());
            }
        });
    }

    private T loadFirstTypeBasedService() {
        Collection<T> instances = NewInstanceServiceLoader.newServiceInstances(classType);
        if (instances.isEmpty()) {
            throw new ShardingConfigurationException("Invalid `%s` SPI, no implementation class load from SPI.", classType.getName());
        }
        return instances.iterator().next();
    }
}
