package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.JdbcFilterValue;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.Operator;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilterBooleanTranslator implements IFilterTranslator<Boolean> {

    /**
     *
     */
    private static final long serialVersionUID = 4926507893727375889L;

    @Override
    public JdbcFilterValue renderToFilter(String key, String column, Operator operator, Boolean firstOperand, Boolean secondOperand) {
        String condition = null;
        Map<String, Object> params = new LinkedHashMap<>();
        String firstParam = key + "_1st";
        if (operator == Operator.Equal || operator == Operator.Like) {
            condition = column + " = :" + firstParam;
            params.put(firstParam, firstOperand);
        } else if (operator == Operator.NotEqual || operator == Operator.NotLike) {
            condition = column + " != :" + firstParam;
            params.put(firstParam, firstOperand);
        }
        return new JdbcFilterValue(condition, params);
    }

}
