package com.senior.cyber.frmk.common.wicket.functional;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import jakarta.persistence.Tuple;
import org.apache.wicket.util.io.IClusterable;

@FunctionalInterface
public interface CellSerializerFunction<T> extends IClusterable {

    ItemPanel apply(String key, Tuple object, T value);

}