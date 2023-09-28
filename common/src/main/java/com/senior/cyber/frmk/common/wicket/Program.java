package com.senior.cyber.frmk.common.wicket;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.util.AbstractJdbcDataProvider;
import com.senior.cyber.frmk.common.wicket.functional.CellSerializerFunction;
import com.senior.cyber.frmk.common.wicket.functional.SerializerFunction;
import jakarta.persistence.Tuple;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Program {

    public static void main(String[] args) {
        List<IColumn<Tuple, ? extends Serializable>> intermediate_browse_column = null;
        AbstractJdbcDataProvider intermediate_browse_provider = null;

        SerializerFunction<Date> serializer = (String s1, Date s2) -> {
            return "";
        };

        CellSerializerFunction<Date> call = (String key, Tuple object, Date value) -> {
            return null;
        };

        intermediate_browse_column.add(intermediate_browse_provider.column(Date.class, Model.of(""), "", "", serializer, call));

        new DataTable("intermediate_browse_table", intermediate_browse_column, intermediate_browse_provider, 20);
    }
}
