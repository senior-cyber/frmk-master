package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.IFilterTranslator;
import org.apache.wicket.util.convert.IConverter;

public interface Convertor<C> extends IConverter<C> {

    Class<C> getType();

    IFilterTranslator<C> getFilterTranslator();

}
