package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.util.io.IClusterable;

import java.util.List;

public interface JdbcFunction extends IClusterable {

    String whereFunction(String propertyExpression);

    String fromFunction(List<String> select, List<String> where, List<String> groupBy, List<String> having);

    String groupByFunction();

    String havingFunction(String propertyExpression);

    String selectFunction(String propertyExpression);

}
