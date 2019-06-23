
package com.hailin.shrine.jdbc.core.common.constant;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

import java.util.Arrays;

/**
 * 支持的数据库
 * 
 */
public enum DatabaseType {
    
    H2("H2"), MySQL("MySQL"), PostgreSQL("PostgreSQL"), Oracle("Oracle"), SQLServer("Microsoft SQL Server");
    
    private final String productName;
    
    DatabaseType(final String productName) {
        this.productName = productName;
    }
    
    /**
     * Get database type enum via database name string.
     * 
     * @param databaseProductName database name string
     * @return database enum
     */
    public static DatabaseType valueFrom(final String databaseProductName) {
        Optional<DatabaseType> databaseTypeOptional = Iterators.tryFind(Arrays.asList(DatabaseType.values()).iterator(), new Predicate<DatabaseType>() {
            
            @Override
            public boolean apply(final DatabaseType input) {
                return input.productName.equals(databaseProductName);
            }
        });
        if (databaseTypeOptional.isPresent()) {
            return databaseTypeOptional.get();
        }
        throw new UnsupportedOperationException(String.format("Can not support database type [%s].", databaseProductName)); 
    }
}
