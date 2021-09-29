package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.JdbcFilterValue;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.Operator;

import java.io.Serializable;

public interface IFilterTranslator<T> extends Serializable {

    JdbcFilterValue renderToFilter(String key, String column, Operator operator, T firstOperand, T secondOperand);

}
