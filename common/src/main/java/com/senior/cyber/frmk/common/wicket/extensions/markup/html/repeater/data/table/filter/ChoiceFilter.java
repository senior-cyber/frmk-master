package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serial;
import java.util.List;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.filter.ChoiceFilter
 */
public class ChoiceFilter extends AbstractFilter {

    @Serial
    private static final long serialVersionUID = 1L;

    private final DropDownChoice<String> choice;

    /**
     * Constructor.
     *
     * @param id         component id
     * @param model      model for the drop down choice component
     * @param form       filter form this component will be attached to
     * @param choices    list of choices, see {@link DropDownChoice}
     * @param autoSubmit if true this filter will submit the form on selection change
     */
    public ChoiceFilter(final String id, final IModel<String> model, final FilterForm form, final IModel<List<String>> choices, final boolean autoSubmit) {
        this(id, model, form, choices, new ChoiceRenderer<>(), autoSubmit);
    }

    /**
     * Constructor
     *
     * @param id         component id
     * @param model      model for the drop down choice component
     * @param form       filter form this component will be attached to
     * @param choices    list of choices, see {@link DropDownChoice}
     * @param autoSubmit if true this filter will submit the form on selection change
     */
    public ChoiceFilter(final String id, final IModel<String> model, final FilterForm form, final List<String> choices, final boolean autoSubmit) {
        this(id, model, form, Model.ofList(choices), new ChoiceRenderer<>(), autoSubmit);
    }

    /**
     * Constructor
     *
     * @param id         component id
     * @param model      model for the drop down choice component
     * @param form       filter form this component will be attached to
     * @param choices    list of choices, see {@link DropDownChoice}
     * @param renderer   choice renderer, see {@link DropDownChoice}
     * @param autoSubmit if true this filter will submit the form on selection change
     */
    public ChoiceFilter(final String id, final IModel<String> model, final FilterForm form, final List<String> choices, final IChoiceRenderer<String> renderer, final boolean autoSubmit) {
        this(id, model, form, Model.ofList(choices), renderer, autoSubmit);
    }

    /**
     * @param id         component id
     * @param model      model for the drop down choice component
     * @param form       filter form this component will be attached to
     * @param choices    list of choices, see {@link DropDownChoice}
     * @param renderer   choice renderer, see {@link DropDownChoice}
     * @param autoSubmit if true this filter will submit the form on selection change
     * @see DropDownChoice
     */
    public ChoiceFilter(final String id, final IModel<String> model, final FilterForm form, final IModel<List<String>> choices, final IChoiceRenderer<String> renderer, final boolean autoSubmit) {
        super(id, form);

        choice = newDropDownChoice("filter", model, choices, renderer);

        if (autoSubmit) {
            choice.add(AttributeModifier.replace("onchange", "this.form.submit();"));
        }
        enableFocusTracking(choice);

        add(choice);
    }

    /**
     * Factory method for the drop down choice component
     *
     * @param id       component id
     * @param model    component model
     * @param choices  choices model
     * @param renderer choice renderer
     * @return created drop down component
     */
    protected DropDownChoice<String> newDropDownChoice(final String id, final IModel<String> model, final IModel<List<String>> choices, final IChoiceRenderer<String> renderer) {
        DropDownChoice<String> dropDownChoice = new DropDownChoice<>(id, model, choices, renderer);
        dropDownChoice.setNullValid(true);
        return dropDownChoice;
    }

    /**
     * @return the DropDownChoice form component created to represent this filter
     */
    public final DropDownChoice<String> getChoice() {
        return choice;
    }

}
