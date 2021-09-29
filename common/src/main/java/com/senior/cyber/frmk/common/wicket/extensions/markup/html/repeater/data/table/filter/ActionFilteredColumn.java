package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.functional.WicketThreeConsumer;
import com.senior.cyber.frmk.common.wicket.functional.WicketTwoFunction;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

public class ActionFilteredColumn<T> extends ActionColumn<T> implements IFilteredColumn<T, String> {

    /**
     *
     */
    private static final long serialVersionUID = -3881419595899328675L;

    protected IModel<String> filter;

    protected IModel<String> clear;

    public ActionFilteredColumn(IModel<String> displayModel, WicketTwoFunction<String, T, List<ActionItem>> actions,
                                WicketThreeConsumer<String, T, AjaxRequestTarget> itemClick) {
        this(displayModel, Model.of("Filter"), Model.of("Clear"), actions, itemClick);
    }

    public ActionFilteredColumn(IModel<String> displayModel, IModel<String> filter, IModel<String> clear,
                                WicketTwoFunction<String, T, List<ActionItem>> actions,
                                WicketThreeConsumer<String, T, AjaxRequestTarget> itemClick) {
        super(displayModel, actions, itemClick);
        this.filter = filter;
        this.clear = clear;
    }

    public ActionFilteredColumn(IModel<String> displayModel, IModel<String> separator,
                                WicketTwoFunction<String, T, List<ActionItem>> actions,
                                WicketThreeConsumer<String, T, AjaxRequestTarget> itemClick) {
        this(displayModel, separator, Model.of("Filter"), Model.of("Clear"), actions, itemClick);
    }

    public ActionFilteredColumn(IModel<String> displayModel, IModel<String> separator, IModel<String> filter,
                                IModel<String> clear, WicketTwoFunction<String, T, List<ActionItem>> actions,
                                WicketThreeConsumer<String, T, AjaxRequestTarget> itemClick) {
        super(displayModel, separator, actions, itemClick);
        this.filter = filter;
        this.clear = clear;
    }

    @Override
    public Component getFilter(String componentId, FilterForm<?> form) {
        return new GoAndClearFilter(componentId, form, this.filter, this.clear);
    }

}
