package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class ChoiceFilter<T>
        extends org.apache.wicket.extensions.markup.html.repeater.data.table.filter.ChoiceFilter<T> {

    /**
     *
     */
    private static final long serialVersionUID = -1218260267563567186L;

    public ChoiceFilter(String id, IModel<T> model, FilterForm<?> form, IModel<? extends List<? extends T>> choices,
                        boolean autoSubmit) {
        super(id, model, form, choices, autoSubmit);
    }

    public ChoiceFilter(String id, IModel<T> model, FilterForm<?> form, List<? extends T> choices, boolean autoSubmit) {
        super(id, model, form, choices, autoSubmit);
    }

    public ChoiceFilter(String id, IModel<T> model, FilterForm<?> form, List<? extends T> choices,
                        IChoiceRenderer<? super T> renderer, boolean autoSubmit) {
        super(id, model, form, choices, renderer, autoSubmit);
    }

    public ChoiceFilter(String id, IModel<T> model, FilterForm<?> form, IModel<? extends List<? extends T>> choices,
                        IChoiceRenderer<? super T> renderer, boolean autoSubmit) {
        super(id, model, form, choices, renderer, autoSubmit);
    }
}