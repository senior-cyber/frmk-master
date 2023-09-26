package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.JdbcColumn;
import com.senior.cyber.frmk.common.wicket.functional.DeserializerFunction;
import jakarta.persistence.Tuple;
import lombok.Getter;
import lombok.Setter;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.io.Serializable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn
 */
public abstract class FilteredJdbcColumn<CellType extends Serializable> extends JdbcColumn<CellType> implements IFilteredColumn<Tuple, CellType> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    protected DeserializerFunction<CellType> deserializer;

    public FilteredJdbcColumn(final IModel<String> displayModel, final String sortKey) {
        super(displayModel, sortKey);
    }

    public FilteredJdbcColumn(final IModel<String> displayModel) {
        super(displayModel);
    }

}
