package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import com.senior.cyber.frmk.common.wicket.functional.WicketThreeConsumer;
import com.senior.cyber.frmk.common.wicket.functional.WicketTwoFunction;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.io.Serial;

public class ItemTextLink<T> extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    public ItemTextLink(String id, IModel<T> rowModel, IModel<?> model, WicketTwoFunction<String, T, ItemCss> itemCss,
                        WicketThreeConsumer<String, T, AjaxRequestTarget> itemClick, String identity) {
        super(id);

        AjaxLink<T> link = new AjaxLink<>("link", rowModel) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                if (itemClick != null) {
                    itemClick.accept(identity, rowModel.getObject(), target);
                }
            }
        };
        add(link);

        Label text = new Label("text", model);
        link.add(text);

        ItemCss htmlLambdaCss = itemCss.apply(identity, rowModel.getObject());
        if (htmlLambdaCss == ItemCss.SUCCESS) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-success"));
        } else if (htmlLambdaCss == ItemCss.PRIMARY) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-primary"));
        } else if (htmlLambdaCss == ItemCss.WARNING) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-warning"));
        } else if (htmlLambdaCss == ItemCss.DANGER) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-danger"));
        } else if (htmlLambdaCss == ItemCss.INFO) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-info"));
        }
    }

    public ItemTextLink(String id, IModel<T> rowModel, IModel<?> model, ItemCss itemCss,
                        WicketThreeConsumer<String, T, AjaxRequestTarget> itemClick, String identity) {
        super(id);

        AjaxLink<T> link = new AjaxLink<>("link", rowModel) {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                if (itemClick != null) {
                    itemClick.accept(identity, rowModel.getObject(), target);
                }
            }
        };
        add(link);

        Label text = new Label("text", model);
        link.add(text);

        ItemCss htmlLambdaCss = itemCss;
        if (htmlLambdaCss == ItemCss.SUCCESS) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-success"));
        } else if (htmlLambdaCss == ItemCss.PRIMARY) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-primary"));
        } else if (htmlLambdaCss == ItemCss.WARNING) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-warning"));
        } else if (htmlLambdaCss == ItemCss.DANGER) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-danger"));
        } else if (htmlLambdaCss == ItemCss.INFO) {
            text.add(AttributeModifier.replace("class", "btn-xs btn-info"));
        }
    }

}
