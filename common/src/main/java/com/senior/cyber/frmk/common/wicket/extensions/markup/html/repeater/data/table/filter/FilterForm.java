package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;

import java.util.Map;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm
 */
public class FilterForm extends Form<Map<String, String>> {

    private static final long serialVersionUID = 1L;

    private static final ResourceReference JS = new JavaScriptResourceReference(FilterForm.class, "wicket-filterform.js");

    private final IFilterStateLocator locator;

    /**
     * @param id      component id
     * @param locator filter state locator
     */
    public FilterForm(final String id, final IFilterStateLocator locator) {
        super(id, new FilterStateModel(locator));

        this.locator = locator;
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(JS));

        response.render(OnLoadHeaderItem.forScript(String.format("Wicket.FilterForm.restore('%s');", getFocusTrackerFieldCssId())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        super.onComponentTagBody(markupStream, openTag);

        getResponse().write(generateHiddenInputMarkup());
    }

    /**
     * Generates the Markup for the hidden input. Can be overridden by subclasses if necessary.
     *
     * @return The markup to be appended to the response
     */
    protected String generateHiddenInputMarkup() {
        String id = Strings.escapeMarkup(getFocusTrackerFieldCssId()).toString();
        String value = getRequest().getPostParameters().getParameterValue(id).toString("");
        String cssClass = getString(Form.HIDDEN_FIELDS_CSS_CLASS_KEY);

        return String.format("<div hidden='' class='%s'><input type='hidden' name='%s' id='%s' value='%s'/><input type='submit'/></div>", cssClass, id, id, Strings.escapeMarkup(value));
    }

    /**
     * @return css id of the hidden form input that keeps track of the focused input field
     */
    public final String getFocusTrackerFieldCssId() {
        return getMarkupId() + "focus";
    }

    /**
     * @return IFilterStateLocator passed to this form
     */
    public final IFilterStateLocator getStateLocator() {
        return locator;
    }

    /**
     * Adds behavior to the form component to allow this form to keep track of the component's focus
     * which will be restored after a form submit.
     *
     * @param fc form component
     */
    public final void enableFocusTracking(final FormComponent<?> fc) {
        fc.add(new Behavior() {
            private static final long serialVersionUID = 1L;

            @Override
            public void bind(Component component) {
                super.bind(component);
                component.setOutputMarkupId(true);
            }

            @Override
            public void onComponentTag(final Component component, final ComponentTag tag) {
                tag.put("onfocus", getFocusTrackingHandler(component));

                super.onComponentTag(component, tag);
            }
        });
    }

    /**
     * Returns the javascript focus handler necessary to notify the form of focus tracking changes
     * on the component
     * <p>
     * Useful when components want to participate in focus tracking but want to add the handler
     * their own way.
     * <p>
     * A unique css id is required on the form component for focus tracking to work.
     *
     * @param component component to
     * @return the javascript focus handler necessary to notify the form of focus tracking changes
     * on the component
     */
    public final String getFocusTrackingHandler(final Component component) {
        return String.format("Wicket.FilterForm.focused(this, '%s');", getFocusTrackerFieldCssId());
    }

}
