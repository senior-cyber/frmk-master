package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.filter.jdbc.FilterBooleanTranslator;
import org.apache.wicket.util.convert.ConversionException;

import java.util.Locale;

public class BooleanConvertor implements Convertor<Boolean> {

    protected FilterBooleanTranslator translator;

    public BooleanConvertor() {
        this.translator = new FilterBooleanTranslator();
    }

    @Override
    public Boolean convertToObject(String value, Locale locale) throws ConversionException {
        if (value == null || "".equals(value)) {
            return false;
        }
        if ("Yes".equalsIgnoreCase(value) ||
                "Y".equalsIgnoreCase(value) ||
                "True".equalsIgnoreCase(value) ||
                "1".equalsIgnoreCase(value)
        ) {
            return true;
        }
        return false;
    }

    @Override
    public String convertToString(Boolean value, Locale locale) {
        if (value == null || !value) {
            return "No";
        } else {
            return "Yes";
        }
    }

    @Override
    public IFilterTranslator<Boolean> getFilterTranslator() {
        return this.translator;
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

}
