package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc.FilterDoubleTranslator;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.convert.ConversionException;

import java.util.Locale;

public class DoubleConvertor implements Convertor<Double> {

    protected FilterDoubleTranslator translator;

    public DoubleConvertor() {
        this.translator = new FilterDoubleTranslator();
    }

    @Override
    public Double convertToObject(String value, Locale locale) throws ConversionException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Double.valueOf(value);
    }

    @Override
    public String convertToString(Double value, Locale locale) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    @Override
    public IFilterTranslator<Double> getFilterTranslator() {
        return this.translator;
    }

    @Override
    public Class<Double> getType() {
        return Double.class;
    }

}
