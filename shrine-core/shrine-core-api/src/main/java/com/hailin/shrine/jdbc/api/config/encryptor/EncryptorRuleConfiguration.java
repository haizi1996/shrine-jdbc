package com.hailin.shrine.jdbc.api.config.encryptor;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hailin.shrine.jdbc.api.config.TypeBasedSPIConfiguration;
import lombok.Getter;

import java.util.Properties;

@Getter
public class EncryptorRuleConfiguration extends TypeBasedSPIConfiguration {

    private final String qualifiedColumns;

    //辅助查询字段
    private String assistedQueryColumns;

    public EncryptorRuleConfiguration(final String type, final String qualifiedColumns, final Properties properties) {
        this(type, qualifiedColumns, "", properties);
    }

    public EncryptorRuleConfiguration(final String type, final String qualifiedColumns, final String assistedQueryColumns, final Properties properties) {
        super(type, properties);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(qualifiedColumns), "qualifiedColumns is required.");
        this.qualifiedColumns = qualifiedColumns;
        this.assistedQueryColumns = null == assistedQueryColumns ? "" : assistedQueryColumns;
    }

}
