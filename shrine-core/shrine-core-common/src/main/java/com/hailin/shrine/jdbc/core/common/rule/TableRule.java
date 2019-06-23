package com.hailin.shrine.jdbc.core.common.rule;

import com.google.common.base.Strings;
import com.hailin.shrine.jdbc.api.config.sharding.KeyGeneratorConfiguration;
import com.hailin.shrine.jdbc.api.config.sharding.TableRuleConfiguration;
import com.hailin.shrine.jdbc.api.spi.keygen.ShardingKeyGenerator;
import com.hailin.shrine.jdbc.core.common.exception.ShardingException;
import com.hailin.shrine.jdbc.core.common.strategy.route.ShardingStrategy;
import com.hailin.shrine.jdbc.core.common.util.InlineExpressionParser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

/**
 * 数据库表规则
 */
@Getter
@ToString(exclude = {"dataNodeIndexMap", "actualTables"})
public final class TableRule {
    private final String logicTable;

    private final List<DataNode> actualDataNodes;

    @Getter(AccessLevel.NONE)
    private final Set<String> actualTables;

    @Getter(AccessLevel.NONE)
    private final Map<DataNode, Integer> dataNodeIndexMap;

    private final ShardingStrategy databaseShardingStrategy;

    private final ShardingStrategy tableShardingStrategy;

    //主键所在的类
    private final String generateKeyColumn;

    private final ShardingKeyGenerator shardingKeyGenerator;

    private final String logicIndex;

    public TableRule(final String defaultDataSourceName, final String logicTableName) {
        logicTable = logicTableName.toLowerCase();
        actualDataNodes = Collections.singletonList(new DataNode(defaultDataSourceName, logicTableName));
        actualTables = getActualTables();
        dataNodeIndexMap = Collections.emptyMap();
        databaseShardingStrategy = null;
        tableShardingStrategy = null;
        generateKeyColumn = null;
        shardingKeyGenerator = null;
        logicIndex = null;
    }

    public TableRule(final Collection<String> dataSourceNames, final String logicTableName) {
        logicTable = logicTableName.toLowerCase();
        dataNodeIndexMap = new HashMap<>(dataSourceNames.size(), 1);
        actualDataNodes = generateDataNodes(logicTableName, dataSourceNames);
        actualTables = getActualTables();
        databaseShardingStrategy = null;
        tableShardingStrategy = null;
        generateKeyColumn = null;
        shardingKeyGenerator = null;
        logicIndex = null;
    }

    public TableRule(final TableRuleConfiguration tableRuleConfig, final ShardingDataSourceNames shardingDataSourceNames, final String defaultGenerateKeyColumn) {
        logicTable = tableRuleConfig.getLogicTable().toLowerCase();
        List<String> dataNodes = new InlineExpressionParser(tableRuleConfig.getActualDataNodes()).splitAndEvaluate();
        dataNodeIndexMap = new HashMap<>(dataNodes.size(), 1);
        actualDataNodes = isEmptyDataNodes(dataNodes)
                ? generateDataNodes(tableRuleConfig.getLogicTable(), shardingDataSourceNames.getDataSourceNames()) : generateDataNodes(dataNodes, shardingDataSourceNames.getDataSourceNames());
        actualTables = getActualTables();
        databaseShardingStrategy = null == tableRuleConfig.getDatabaseShardingStrategyConfig() ? null : ShardingStrategyFactory.newInstance(tableRuleConfig.getDatabaseShardingStrategyConfig());
        tableShardingStrategy = null == tableRuleConfig.getTableShardingStrategyConfig() ? null : ShardingStrategyFactory.newInstance(tableRuleConfig.getTableShardingStrategyConfig());
        generateKeyColumn = getGenerateKeyColumn(tableRuleConfig.getKeyGeneratorConfig(), defaultGenerateKeyColumn);
        shardingKeyGenerator = containsKeyGeneratorConfiguration(tableRuleConfig)
                ? new ShardingKeyGeneratorServiceLoader().newService(tableRuleConfig.getKeyGeneratorConfig().getType(), tableRuleConfig.getKeyGeneratorConfig().getProperties()) : null;
        logicIndex = null == tableRuleConfig.getLogicIndex() ? null : tableRuleConfig.getLogicIndex().toLowerCase();
    }


    /**
     * 获取所有的实际表
     */
    private Set<String> getActualTables() {
        Set<String> result = new HashSet<>(actualDataNodes.size(), 1);
        for (DataNode each : actualDataNodes) {
            result.add(each.getTableName());
        }
        return result;
    }

    /**
     * 数据库表规则配置是否包含主键配置
     * @param tableRuleConfiguration 数据库表规则配置
     */
    private boolean containsKeyGeneratorConfiguration(final TableRuleConfiguration tableRuleConfiguration) {
        return null != tableRuleConfiguration.getKeyGeneratorConfig() && !Strings.isNullOrEmpty(tableRuleConfiguration.getKeyGeneratorConfig().getType());
    }

    /**
     * 获取主键配置的列
     * @param keyGeneratorConfiguration 主键配置
     * @param defaultGenerateKeyColumn 默认的列
     */
    private String getGenerateKeyColumn(final KeyGeneratorConfiguration keyGeneratorConfiguration, final String defaultGenerateKeyColumn) {
        if (null != keyGeneratorConfiguration && !Strings.isNullOrEmpty(keyGeneratorConfiguration.getColumn())) {
            return keyGeneratorConfiguration.getColumn();
        }
        return defaultGenerateKeyColumn;
    }

    private boolean isEmptyDataNodes(final List<String> dataNodes) {
        return null == dataNodes || dataNodes.isEmpty();
    }

    /**
     * 封装成数据节点
     * @param logicTable 逻辑表
     * @param dataSourceNames 数据源名
     */
    private List<DataNode> generateDataNodes(final String logicTable, final Collection<String> dataSourceNames) {
        List<DataNode> result = new LinkedList<>();
        int index = 0;
        for (String each : dataSourceNames) {
            DataNode dataNode = new DataNode(each, logicTable);
            result.add(dataNode);
            dataNodeIndexMap.put(dataNode, index);
            index++;
        }
        return result;
    }

    /**
     * 封装成数据节点
     * @param actualDataNodes 实际的数据节点 数据源名.数据库表名
     * @param dataSourceNames 数据源名称
     */
    private List<DataNode> generateDataNodes(final List<String> actualDataNodes, final Collection<String> dataSourceNames) {
        List<DataNode> result = new LinkedList<>();
        int index = 0;
        for (String each : actualDataNodes) {
            DataNode dataNode = new DataNode(each);
            if (!dataSourceNames.contains(dataNode.getDataSourceName())) {
                throw new ShardingException("Cannot find data source in sharding rule, invalid actual data node is: '%s'", each);
            }
            result.add(dataNode);
            dataNodeIndexMap.put(dataNode, index);
            index++;
        }
        return result;
    }

    /**
     * 获取数据节点的组
     *key 是数据源名 ， value是数据源的表集合
     */
    public Map<String, List<DataNode>> getDataNodeGroups() {
        Map<String, List<DataNode>> result = new LinkedHashMap<>(actualDataNodes.size(), 1);
        for (DataNode each : actualDataNodes) {
            String dataSourceName = each.getDataSourceName();
            if (!result.containsKey(dataSourceName)) {
                result.put(dataSourceName, new LinkedList<DataNode>());
            }
            result.get(dataSourceName).add(each);
        }
        return result;
    }
    /**
     * 获取实际的数据源
     *
     */
    public Collection<String> getActualDatasourceNames() {
        Collection<String> result = new LinkedHashSet<>(actualDataNodes.size());
        for (DataNode each : actualDataNodes) {
            result.add(each.getDataSourceName());
        }
        return result;
    }

    /**
     *
     * 获取数据源下的所有表名
     * @param targetDataSource  目标数据源名
     */
    public Collection<String> getActualTableNames(final String targetDataSource) {
        Collection<String> result = new LinkedHashSet<>(actualDataNodes.size());
        for (DataNode each : actualDataNodes) {
            if (targetDataSource.equals(each.getDataSourceName())) {
                result.add(each.getTableName());
            }
        }
        return result;
    }

    int findActualTableIndex(final String dataSourceName, final String actualTableName) {
        DataNode dataNode = new DataNode(dataSourceName, actualTableName);
        return dataNodeIndexMap.containsKey(dataNode) ? dataNodeIndexMap.get(dataNode) : -1;
    }

    boolean isExisted(final String actualTableName) {
        return actualTables.contains(actualTableName);
    }
}
