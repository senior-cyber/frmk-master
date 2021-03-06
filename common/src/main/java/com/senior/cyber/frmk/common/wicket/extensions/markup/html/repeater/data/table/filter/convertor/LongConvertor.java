package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc.FilterLongTranslator;
import org.apache.wicket.util.convert.ConversionException;

import java.util.Locale;

public class LongConvertor implements Convertor<Long> {

    protected FilterLongTranslator translator;

    public LongConvertor() {
        this.translator = new FilterLongTranslator();
    }

    @Override
    public Long convertToObject(String value, Locale locale) throws ConversionException {
        return Long.valueOf(value);
    }

    @Override
    public String convertToString(Long value, Locale locale) {
        return String.valueOf(value);
    }

    @Override
    public IFilterTranslator<Long> getFilterTranslator() {
        return this.translator;
    }

    @Override
    public Class<Long> getType() {
        return Long.class;
    }

}
