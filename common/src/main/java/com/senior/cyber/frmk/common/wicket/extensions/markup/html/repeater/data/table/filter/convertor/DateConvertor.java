package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc.FilterDateTranslator;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.util.convert.ConversionException;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateConvertor implements Convertor<Date> {

    protected FilterDateTranslator translator;

    public DateConvertor() {
        this.translator = new FilterDateTranslator();
    }

    @Override
    public Date convertToObject(String value, Locale locale) throws ConversionException {
        LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Date.valueOf(localDate);
    }

    @Override
    public String convertToString(Date value, Locale locale) {
        return DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(value);
    }

    @Override
    public IFilterTranslator<Date> getFilterTranslator() {
        return this.translator;
    }

    @Override
    public Class<Date> getType() {
        return Date.class;
    }

}
