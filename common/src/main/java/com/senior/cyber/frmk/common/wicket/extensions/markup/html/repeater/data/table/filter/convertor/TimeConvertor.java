package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc.FilterTimeTranslator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.util.convert.ConversionException;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeConvertor implements Convertor<Time> {

    protected FilterTimeTranslator translator;

    public TimeConvertor() {
        this.translator = new FilterTimeTranslator();
    }

    @Override
    public Time convertToObject(String value, Locale locale) throws ConversionException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        LocalTime localTime = LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm:ss"));
        return Time.valueOf(localTime);
    }

    @Override
    public String convertToString(Time value, Locale locale) {
        if (value == null) {
            return "";
        }
        return DateFormatUtils.ISO_8601_EXTENDED_TIME_FORMAT.format(value);
    }

    @Override
    public IFilterTranslator<Time> getFilterTranslator() {
        return this.translator;
    }

    @Override
    public Class<Time> getType() {
        return Time.class;
    }

}
