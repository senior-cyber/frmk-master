package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc.FilterDatetimeTranslator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.util.convert.ConversionException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeConvertor implements Convertor<Timestamp> {

    protected FilterDatetimeTranslator translator;

    public DateTimeConvertor() {
        this.translator = new FilterDatetimeTranslator();
    }

    @Override
    public Timestamp convertToObject(String value, Locale locale) throws ConversionException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        return Timestamp.valueOf(localDateTime);
    }

    @Override
    public String convertToString(Timestamp value, Locale locale) {
        if (value == null) {
            return "";
        }
        return DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(value);
    }

    @Override
    public IFilterTranslator<Timestamp> getFilterTranslator() {
        return this.translator;
    }

    @Override
    public Class<Timestamp> getType() {
        return Timestamp.class;
    }

}
