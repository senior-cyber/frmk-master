package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.senior.cyber.frmk.common.wicket.functional.WicketThreeConsumer;
import com.senior.cyber.frmk.common.wicket.functional.WicketTwoFunction;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ActionColumn<RowType, CellType extends Serializable> extends AbstractColumn<RowType, CellType> {

    @Serial
    private static final long serialVersionUID = 1L;

    protected WicketThreeConsumer<String, RowType, AjaxRequestTarget> itemClick;

    protected WicketTwoFunction<String, RowType, List<ActionItem>> actions;

    protected IModel<String> separator;

    public ActionColumn(IModel<String> displayModel, WicketTwoFunction<String, RowType, List<ActionItem>> actions,
                        WicketThreeConsumer<String, RowType, AjaxRequestTarget> itemClick) {
        super(displayModel);
        this.actions = actions;
        this.itemClick = itemClick;
        this.separator = Model.of(" ");
    }

    public ActionColumn(IModel<String> displayModel, IModel<String> separator,
                        WicketTwoFunction<String, RowType, List<ActionItem>> actions,
                        WicketThreeConsumer<String, RowType, AjaxRequestTarget> itemClick) {
        super(displayModel);
        this.actions = actions;
        this.itemClick = itemClick;
        this.separator = separator;
    }

    @Override
    public void populateItem(Item<IColumn<RowType, ? extends CellType>> item, String componentId, IModel<RowType> rowModel) {
        List<ActionItem> actionItems = this.actions.apply(getDisplayModel().getObject(), rowModel.getObject());
        if (actionItems == null || actionItems.isEmpty()) {
            Label label = new Label(componentId, "");
            item.add(label);
        } else {
            Collections.sort(actionItems);
            RepeatingView view = new RepeatingView(componentId);
            item.add(view);
            boolean first = true;
            for (ActionItem actionItem : actionItems) {
                if (!first) {
                    Label label = new Label(view.newChildId(), this.separator);
                    view.add(label);
                }
                if (!actionItem.isIcon()) {
                    view.add(new ItemTextLink<>(view.newChildId(), rowModel, actionItem.getLabel(), actionItem.getCss(),
                            this.itemClick, actionItem.getLink()));
                } else {
                    view.add(new ItemIconLink<>(view.newChildId(), rowModel, actionItem.getIconClass(), this.itemClick,
                            actionItem.getLink()));
                }
                first = false;
            }
        }
    }

}
