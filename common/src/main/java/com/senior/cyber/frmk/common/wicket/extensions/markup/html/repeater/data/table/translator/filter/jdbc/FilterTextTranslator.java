package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.JdbcFilterValue;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.Operator;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilterTextTranslator implements IFilterTranslator<String> {

    /**
     *
     */
    private static final long serialVersionUID = 4926507893727375889L;

    private static final char HIDDEN_SPACE = '\u200B';

    @Override
    public JdbcFilterValue renderToFilter(String key, String column, Operator operator, String firstOperand, String secondOperand) {
        Map<String, Object> params = new LinkedHashMap<>();
        String condition = null;
        String firstParam = key + "_1st";
        if (operator == Operator.Equal) {
            if (firstOperand == null || "".equals(firstOperand)) {
                condition = "(" + column + " = :" + firstParam + " OR " + column + " IS NULL)";
                params.put(firstParam, "");
            } else {
                condition = column + " = :" + firstParam;
                params.put(firstParam, firstOperand);
            }
        } else if (operator == Operator.NotEqual) {
            if (firstOperand == null || "".equals(firstOperand)) {
                condition = "(" + column + " != :" + firstParam + " AND " + column + " IS NOT NULL)";
                params.put(firstParam, "");
            } else {
                condition = column + " != :" + firstParam;
                params.put(firstParam, firstOperand);
            }
        } else if (operator == Operator.Like) {
            if (hasLikeRegx((String) firstOperand)) {
                condition = column + " RLIKE :" + firstParam;
                params.put(firstParam, buildLikeRegxExpression((String) firstOperand));
            } else {
                condition = column + " LIKE :" + firstParam;
                params.put(firstParam, buildLikeExpression((String) firstOperand));
            }
        } else if (operator == Operator.NotLike) {
            if (hasLikeRegx((String) firstOperand)) {
                condition = column + " NOT RLIKE :" + firstParam;
                params.put(firstParam, buildLikeRegxExpression((String) firstOperand));
            } else {
                condition = column + " NOT LIKE :" + firstParam;
                params.put(firstParam, buildLikeExpression((String) firstOperand));
            }
        }
        return new JdbcFilterValue(condition, params);
    }

    protected boolean hasLikeRegx(String searchText) {
        for (char tmp : StringUtils.trimToEmpty(searchText).toCharArray()) {
            if (tmp == ' ' || tmp == HIDDEN_SPACE) {
                return true;
            }
        }
        return false;
    }

    protected String buildLikeRegxExpression(String searchText) {
        StringBuffer result = new StringBuffer(searchText.length());
        boolean space = false;
        for (char tmp : StringUtils.trimToEmpty(searchText).toCharArray()) {
            if (tmp == ' ' || tmp == HIDDEN_SPACE) {
                if (!space) {
                    result.append('|');
                    space = true;
                }
            } else {
                space = false;
                result.append(Character.toLowerCase(tmp));
            }
        }
        return "^.*(" + result.toString() + ").*$";
    }

    protected String buildLikeExpression(String searchText) {
        return StringUtils.trimToEmpty(searchText) + "%";
    }

}
