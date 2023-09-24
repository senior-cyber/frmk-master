package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.functional.WicketThreeConsumer;
import com.senior.cyber.frmk.common.wicket.functional.WicketTwoFunction;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.List;

public class ItemIconLink<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public ItemIconLink(String id, IModel<T> rowModel, WicketTwoFunction<String, T, ItemCss> itemCss,
                        WicketThreeConsumer<String, T, AjaxRequestTarget> itemClick, String identity) {
        super(id);

        AjaxLink<T> link = new AjaxLink<>("link", rowModel) {
            /**
             *
             */
            private static final long serialVersionUID = -7424883804625007512L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                if (itemClick != null) {
                    itemClick.accept(identity, rowModel.getObject(), target);
                }
            }
        };
        add(link);

        Label icon = new Label("icon");
        link.add(icon);
        icon.add(AttributeModifier.replace("class", StringUtils.join(itemCss, " ")));
    }

    public ItemIconLink(String id, IModel<T> rowModel, List<String> itemCss,
                        WicketThreeConsumer<String, T, AjaxRequestTarget> itemClick, String identity) {
        super(id);

        AjaxLink<T> link = new AjaxLink<>("link", rowModel) {
            /**
             *
             */
            private static final long serialVersionUID = 807411944097534629L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                if (itemClick != null) {
                    itemClick.accept(identity, rowModel.getObject(), target);
                }
            }
        };
        add(link);

        Label icon = new Label("icon");
        link.add(icon);

        icon.add(AttributeModifier.replace("class", StringUtils.join(itemCss, " ")));
    }

}
