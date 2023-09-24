package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;//package com.senior.cyber.media.backend.web.test;
//
//import org.apache.wicket.Component;
//import org.apache.wicket.markup.html.form.IChoiceRenderer;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.PropertyModel;
//
//import java.io.Serializable;
//import java.util.List;
//
//public class ChoiceFilteredPropertyColumn<T extends Serializable> extends FilteredPropertyColumn<T> {
//
//    private static final long serialVersionUID = 1L;
//    private final IModel<List<String>> filterChoices;
//
//    /**
//     * @param displayModel
//     * @param sortProperty
//     * @param propertyExpression
//     * @param filterChoices      collection choices used in the choice filter
//     */
//    public ChoiceFilteredPropertyColumn(final IModel<String> displayModel, final String sortProperty, final String propertyExpression, final IModel<List<String>> filterChoices) {
//        super(displayModel, sortProperty, propertyExpression);
//        this.filterChoices = filterChoices;
//    }
//
//    /**
//     * @param displayModel
//     * @param propertyExpression
//     * @param filterChoices      collection of choices used in the choice filter
//     */
//    public ChoiceFilteredPropertyColumn(final IModel<String> displayModel, final String propertyExpression, final IModel<List<String>> filterChoices) {
//        super(displayModel, propertyExpression);
//        this.filterChoices = filterChoices;
//    }
//
//    /**
//     * @see org.apache.wicket.model.IDetachable#detach()
//     */
//    @Override
//    public void detach() {
//        super.detach();
//        if (filterChoices != null) {
//            filterChoices.detach();
//        }
//    }
//
//    /**
//     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn#getFilter(java.lang.String,
//     * org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm)
//     */
//    @Override
//    public Component getFilter(final String componentId, final FilterForm form) {
//        ChoiceFilter filter = new ChoiceFilter(componentId, getFilterModel(form), form, filterChoices, enableAutoSubmit());
//
//        IChoiceRenderer<String> renderer = getChoiceRenderer();
//        if (renderer != null) {
//            filter.getChoice().setChoiceRenderer(renderer);
//        }
//        return filter;
//    }
//
//    /**
//     * Returns the model that will be passed on to the text filter. Users can override this method
//     * to change the model.
//     *
//     * @param form filter form
//     * @return model passed on to the text filter
//     */
//    protected IModel<String> getFilterModel(final FilterForm form) {
//        return new PropertyModel<>(form.getDefaultModel(), getPropertyExpression());
//    }
//
//    /**
//     * Returns true if the constructed choice filter should autosubmit the form when its value is
//     * changed.
//     *
//     * @return true to make choice filter autosubmit, false otherwise
//     */
//    protected boolean enableAutoSubmit() {
//        return true;
//    }
//
//    /**
//     * Returns choice renderer that will be used to create the choice filter
//     *
//     * @return choice renderer that will be used to create the choice filter
//     */
//    protected IChoiceRenderer<String> getChoiceRenderer() {
//        return null;
//    }
//
//    /**
//     * @return filter choices model
//     */
//    protected final IModel<List<String>> getFilterChoices() {
//        return filterChoices;
//    }
//
//
//}
