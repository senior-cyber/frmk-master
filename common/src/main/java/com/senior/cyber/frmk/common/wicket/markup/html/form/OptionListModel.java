package com.senior.cyber.frmk.common.wicket.markup.html.form;

import com.senior.cyber.frmk.common.wicket.markup.html.form.select2.Option;
import com.senior.cyber.frmk.jdbc.query.GenericSelectQuery;
import com.senior.cyber.frmk.jdbc.query.SelectQuery;
import org.apache.wicket.model.util.ListModel;

import java.util.ArrayList;
import java.util.List;

public abstract class OptionListModel extends ListModel<Option> {

    private static final long serialVersionUID = 1L;

    private List<String> where = null;

    public OptionListModel(String table, String idField, String textField) {
        this(table, idField, textField, new ArrayList<>());
    }

    public OptionListModel(String table, String idField, String textField,
                           List<String> where) {
        this.where = where;
        SelectQuery selectQuery = new GenericSelectQuery(table);
        selectQuery.addField(idField + " AS id");
        selectQuery.addField(textField + " AS text");
        selectQuery.addOrderBy(textField + " ASC");
        if (this.where != null && !this.where.isEmpty()) {
            for (String wh : this.where) {
                selectQuery.addWhere(wh);
            }
        }
        List<Option> options = queryOptions(selectQuery.toSQL());
        setObject(options);
    }

    public abstract List<Option> queryOptions(String query);

}
