package com.senior.cyber.frmk.common.provider;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator.ISortTranslator;

public interface IFieldProvider {

    <T> void selectNormalColumn(String key, String column, Convertor<T> convertor);

    <T> void selectAggregateColumn(String key, String column, Convertor<T> convertor);

    void registerFilter(String key, Convertor<?> convertor);

    void registerSort(String key, ISortTranslator sortTranslator);

}
