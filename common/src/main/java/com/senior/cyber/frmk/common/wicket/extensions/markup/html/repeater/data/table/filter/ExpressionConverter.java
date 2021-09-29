package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import java.util.Locale;

public class ExpressionConverter<T> implements IConverter<Expression<T>> {

    /**
     *
     */
    private static final long serialVersionUID = -4460814600995037209L;

    private final String key;

    private Convertor<T> convertor;

    private IFilterTranslator<T> translator;

    public ExpressionConverter(String key, Convertor<T> convertor) {
        this.key = key;
        this.convertor = convertor;
        this.translator = convertor.getFilterTranslator();
    }

    @Override
    public Expression<T> convertToObject(String value, Locale locale) throws ConversionException {
        return new Expression<>(this.key, value, this.convertor, this.translator);
    }

    @Override
    public String convertToString(Expression value, Locale locale) {
        if (value == null) {
            return null;
        } else {
            return value.getFilter();
        }
    }

    public Convertor<T> getConvertor() {
        return convertor;
    }

}
