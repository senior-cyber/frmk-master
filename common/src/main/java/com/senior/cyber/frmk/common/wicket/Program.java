package com.senior.cyber.frmk.common.wicket;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.util.AbstractJdbcDataProvider;
import jakarta.persistence.Tuple;

import java.io.Serializable;
import java.util.List;

public class Program {

    public static void main(String[] args) {
        List<IColumn<Tuple, Serializable>> intermediate_browse_column = null;
        AbstractJdbcDataProvider intermediate_browse_provider = null;

        new DataTable<>("intermediate_browse_table", intermediate_browse_column, intermediate_browse_provider, 20);
    }
}
