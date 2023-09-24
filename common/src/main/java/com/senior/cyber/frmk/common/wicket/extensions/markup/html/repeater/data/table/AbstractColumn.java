package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import lombok.Getter;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
 */
public abstract class AbstractColumn implements IStyledColumn {

    private static final long serialVersionUID = 1L;

    @Getter
    private final IModel<String> displayModel;

    private final String sortKey;

    /**
     * @param displayModel model used to generate header text
     * @param sortKey      sort key this column represents
     */
    public AbstractColumn(final IModel<String> displayModel, final String sortKey) {
        this.displayModel = displayModel;
        this.sortKey = sortKey;
    }

    /**
     * @param displayModel model used to generate header text
     */
    public AbstractColumn(final IModel<String> displayModel) {
        this(displayModel, null);
    }

    @Override
    public String getSortKey() {
        return sortKey;
    }

    @Override
    public Component getHeader(final String componentId) {
        return new Label(componentId, getDisplayModel());
    }

    @Override
    public void detach() {
        if (displayModel != null) {
            displayModel.detach();
        }
    }

    @Override
    public String getCssClass() {
        return null;
    }

}
