package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.io.Serial;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.GoFilter
 */
public class GoFilter extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    protected static final IModel<String> DEFAULT_GO_MODEL = new ResourceModel("datatable.go", "filter");

    private final Button go;

    /**
     * Constructor
     * <p>
     * This constructor will use the default model for the button's text
     *
     * @param id component id
     */
    public GoFilter(final String id) {
        this(id, DEFAULT_GO_MODEL);
    }

    /**
     * Constructor
     *
     * @param id      component id
     * @param goModel model for the button's text
     */
    public GoFilter(final String id, final IModel<String> goModel) {
        super(id);

        go = new Button("go", goModel) {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit() {
                onGoSubmit(this);
            }
        };

        add(go);
    }

    protected Button getGoButton() {
        return go;
    }

    /**
     * This method can be overridden by subclasses to provide non-standard behavior for the 'go'
     * button.
     *
     * @param button the 'go' button, can be used to get to the Form object and through that to the
     *               filter state object by retrieving the form's model object
     */
    protected void onGoSubmit(final Button button) {
    }

}
