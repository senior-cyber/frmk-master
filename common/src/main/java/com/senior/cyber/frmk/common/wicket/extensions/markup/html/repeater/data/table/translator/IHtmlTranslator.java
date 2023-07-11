package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.translator;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

import jakarta.persistence.Tuple;

public interface IHtmlTranslator<T> extends IClusterable {

    default ItemPanel htmlColumn(String key, IModel<String> display, Tuple object) {
        return null;
    }

}
