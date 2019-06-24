package com.hailin.shrine.sharding.jdbc.core.common.common.lexer.analyzer;

import com.google.common.collect.Maps;
import com.hailin.shrine.jdbc.core.parse.common.lexer.token.DefaultKeyword;
import com.hailin.shrine.jdbc.core.parse.common.lexer.token.Keyword;
import com.hailin.shrine.jdbc.core.parse.common.lexer.token.TokenType;

import java.util.Map;

/**
 * Token的字典
 * @author zhanghailin
 */
public class Dictionary {

    private final Map<String , Keyword> tokens = Maps.newHashMap();

    public Dictionary( final Keyword... dialectKeywords){
        fill(dialectKeywords);
    }
    private void fill(final Keyword... dialectKeywords) {
        for (DefaultKeyword each : DefaultKeyword.values()) {
            tokens.put(each.name(), each);
        }
        for (Keyword each : dialectKeywords) {
            tokens.put(each.toString(), each);
        }
    }

    TokenType findTokenType(final String literals, final TokenType defaultTokenType) {
        String key = null == literals ? null : literals.toUpperCase();
        return tokens.containsKey(key) ? tokens.get(key) : defaultTokenType;
    }

    TokenType findTokenType(final String literals) {
        String key = null == literals ? null : literals.toUpperCase();
        if (tokens.containsKey(key)) {
            return tokens.get(key);
        }
        throw new IllegalArgumentException();
    }
}
