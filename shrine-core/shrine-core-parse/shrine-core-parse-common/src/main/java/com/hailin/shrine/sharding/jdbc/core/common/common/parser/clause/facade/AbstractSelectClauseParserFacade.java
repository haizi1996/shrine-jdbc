package com.hailin.shrine.sharding.jdbc.core.common.common.parser.clause.facade;

import com.hailin.shrine.sharding.jdbc.core.common.common.parser.clause.SelectListClauseParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 选择字句的门面
 */
@RequiredArgsConstructor
@Getter
public abstract class AbstractSelectClauseParserFacade {

    private final SelectListClauseParser selectListClauseParser;

    private final TableReferencesClauseParser tableReferencesClauseParser;

    private final WhereClauseParser whereClauseParser;

    private final GroupByClauseParser groupByClauseParser;

    private final HavingClauseParser havingClauseParser;

    private final OrderByClauseParser orderByClauseParser;

    private final SelectRestClauseParser selectRestClauseParser;
}
