package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.io.Serial;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.GoAndClearFilter
 */
public class GoAndClearFilter extends GoFilter {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final IModel<String> DEFAULT_CLEAR_MODEL = new ResourceModel("datatable.clear", "clear");

    private final Button clear;

    private final Object originalState;

    /**
     * Constructor
     * <p>
     * This constructor will use default models for the 'clear' and 'go' button labels.
     * Uses the form's model object as an original state
     *
     * @param id   component id
     * @param form filter form of the filter toolbar
     */
    public GoAndClearFilter(final String id, final FilterForm form) {
        this(id, form, DEFAULT_GO_MODEL, DEFAULT_CLEAR_MODEL);
    }

    /**
     * Constructor.
     * Uses the form's model object as an original state
     *
     * @param id         component id
     * @param form       filter form of the filter toolbar
     * @param goModel    model for the label of the 'go' button
     * @param clearModel model for the label of the 'clear' button
     */
    public GoAndClearFilter(final String id, final FilterForm form, final IModel<String> goModel, final IModel<String> clearModel) {
        this(id, goModel, clearModel, WicketObjects.cloneObject(form.getDefaultModelObject()));
    }

    /**
     * Constructor
     *
     * @param id            component id
     * @param goModel       model for the label of the 'go' button
     * @param clearModel    model for the label of the 'clear' button
     * @param originalState the object to use as original state
     */
    public GoAndClearFilter(final String id, final IModel<String> goModel, final IModel<String> clearModel, Object originalState) {
        super(id, goModel);

        this.originalState = originalState;

        clear = new Button("clear", clearModel) {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit() {
                onClearSubmit(this);
            }
        };

        clear.setDefaultFormProcessing(true);

        add(clear);
    }

    /**
     * @return button component representing the clear button
     */
    protected Button getClearButton() {
        return clear;
    }

    /**
     * This method should be implemented by subclasses to provide behavior for the clear button.
     *
     * @param button the 'clear' button
     */
    @SuppressWarnings("unchecked")
    protected void onClearSubmit(final Button button) {
        Form<Object> form = (Form<Object>) button.getForm();
        form.setDefaultModelObject(WicketObjects.cloneObject(originalState));
    }

}
