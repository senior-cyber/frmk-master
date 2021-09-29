package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.JdbcFilterValue;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.Operator;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilterLongTranslator implements IFilterTranslator<Long> {

    /**
     *
     */
    private static final long serialVersionUID = 4926507893727375889L;

    @Override
    public JdbcFilterValue renderToFilter(String key, String column, Operator operator, Long firstOperand, Long secondOperand) {
        String condition = null;
        Map<String, Object> params = new LinkedHashMap<>();
        String firstParam = key + "_1st";
        String secondParam = key + "_2nd";
        if (operator == Operator.Equal || operator == Operator.Like) {
            condition = column + " = :" + firstParam;
            params.put(firstParam, firstOperand);
        } else if (operator == Operator.NotEqual || operator == Operator.NotLike) {
            condition = column + " != :" + firstParam;
            params.put(firstParam, firstOperand);
        } else if (operator == Operator.GreaterThan) {
            condition = column + " > :" + firstParam;
            params.put(firstParam, firstOperand);
        } else if (operator == Operator.GreaterThanOrEqual) {
            condition = column + " >= :" + firstParam;
            params.put(firstParam, firstOperand);
        } else if (operator == Operator.LessThan) {
            condition = column + " < :" + firstParam;
            params.put(firstParam, firstOperand);
        } else if (operator == Operator.LessThanOrEqual) {
            condition = column + " <= :" + firstParam;
            params.put(firstParam, firstOperand);
        } else if (operator == Operator.Between) {
            if (firstOperand < secondOperand) {
                condition = column + " BETWEEN :" + firstParam + " AND :" + secondParam;
            } else {
                condition = column + " BETWEEN :" + secondParam + " AND :" + firstParam;
            }
            params.put(firstParam, firstOperand);
            params.put(secondParam, secondOperand);
        } else if (operator == Operator.NotBetween) {
            if (firstOperand < secondOperand) {
                condition = column + " NOT BETWEEN :" + firstParam + " AND :" + secondParam;
            } else {
                condition = column + " NOT BETWEEN :" + secondParam + " AND :" + firstParam;
            }
            params.put(firstParam, firstOperand);
            params.put(secondParam, secondOperand);
        }
        return new JdbcFilterValue(condition, params);
    }

}
