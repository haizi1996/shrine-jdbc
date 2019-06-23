package com.hailin.shrine.jdbc.api.config.encryptor;

import com.hailin.shrine.jdbc.api.config.RuleConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *加密规则配置
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EncryptRuleConfiguration implements RuleConfiguration {

    private Map<String, EncryptorRuleConfiguration> encryptorRuleConfigs = new LinkedHashMap<>();
}
