package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.convert.ConversionException;

import java.io.Serializable;

public class Expression<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -676351625506599512L;

    private Convertor<T> convertor;

    private IFilterTranslator<T> translator;

    private T firstOperand;

    private Operator operator;

    private T secondOperand;

    private String filter;

    private String key;

    public Expression(String key, String filter, Convertor<T> convertor, IFilterTranslator<T> translator) throws ConversionException {
        this.convertor = convertor;
        this.translator = translator;
        this.key = key;
        this.filter = StringUtils.trimToEmpty(filter);
        if (StringUtils.startsWith(filter, "!= ")) {
            this.operator = Operator.NotEqual;
            this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(StringUtils.substring(filter, 3)), null);
        } else if (StringUtils.startsWith(filter, ">= ")) {
            this.operator = Operator.GreaterThanOrEqual;
            this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(StringUtils.substring(filter, 3)), null);
        } else if (StringUtils.startsWith(filter, "<= ")) {
            this.operator = Operator.LessThanOrEqual;
            this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(StringUtils.substring(filter, 3)), null);
        } else if (StringUtils.startsWith(filter, "= ")) {
            this.operator = Operator.Equal;
            this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(StringUtils.substring(filter, 2)), null);
        } else if (StringUtils.startsWithIgnoreCase(filter, "like")) {
            this.operator = Operator.Like;
            this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(StringUtils.substring(filter, 4)), null);
        } else if (StringUtils.startsWithIgnoreCase(filter, "not like")) {
            this.operator = Operator.NotLike;
            this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(StringUtils.substring(filter, 8)), null);
        } else if (StringUtils.startsWith(filter, "> ")) {
            this.operator = Operator.GreaterThan;
            this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(StringUtils.substring(filter, 2)), null);
        } else if (StringUtils.startsWith(filter, "< ")) {
            this.operator = Operator.LessThan;
            this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(StringUtils.substring(filter, 2)), null);
        } else if (StringUtils.containsIgnoreCase(filter, " to ")) {
            int mid = StringUtils.indexOfIgnoreCase(filter, " to ");
            this.operator = Operator.Between;
            String temp = StringUtils.trimToEmpty(StringUtils.substring(filter, 0, mid));
            if (StringUtils.startsWithIgnoreCase(temp, "not between ")) {
                this.operator = Operator.NotBetween;
                this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(temp.substring(12)), null);
            } else {
                this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(temp), null);
            }
            this.secondOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(StringUtils.substring(filter, mid + 4)), null);
        } else if (StringUtils.equals(filter, "=")) {
            this.operator = Operator.Equal;
            this.firstOperand = null;
        } else if (StringUtils.equals(filter, "!=")) {
            this.operator = Operator.NotEqual;
            this.firstOperand = null;
        } else {
            if (StringUtils.contains(filter, "%")) {
                this.operator = Operator.Like;
                this.firstOperand = this.convertor.convertToObject(StringUtils.trimToEmpty(filter), null);
            } else {
                this.operator = Operator.Equal;
                this.firstOperand = this.convertor.convertToObject(filter, null);
            }
        }
    }

    public T getFirstOperand() {
        return firstOperand;
    }

    public Operator getOperator() {
        return operator;
    }

    public T getSecondOperand() {
        return secondOperand;
    }

    public String getFilter() {
        return filter;
    }

    public String getKey() {
        return key;
    }

    public IFilterTranslator<T> getTranslator() {
        return this.translator;
    }

    public JdbcFilterValue renderToFilter(String key, String column) {
        return this.translator.renderToFilter(key, column, getOperator(), getFirstOperand(), getSecondOperand());
    }

}