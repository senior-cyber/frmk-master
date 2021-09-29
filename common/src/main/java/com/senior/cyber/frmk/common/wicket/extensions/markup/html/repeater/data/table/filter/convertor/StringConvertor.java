package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc.FilterTextTranslator;
import org.apache.wicket.util.convert.ConversionException;

import java.util.Locale;

public class StringConvertor implements Convertor<String> {

    protected FilterTextTranslator translator;

    public StringConvertor() {
        this.translator = new FilterTextTranslator();
    }

    @Override
    public String convertToObject(String value, Locale locale) throws ConversionException {
        return value;
    }

    @Override
    public String convertToString(String value, Locale locale) {
        return value;
    }

    @Override
    public IFilterTranslator<String> getFilterTranslator() {
        return this.translator;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

}
