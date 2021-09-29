package com.senior.cyber.frmk.common.wicket.widget;

import com.senior.cyber.frmk.common.wicket.markup.html.panel.Panel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultFooterWidget extends Panel {

    protected Label footer_widget_version_field;
    protected String footer_widget_version_value;

    protected Label footer_widget_year_field;
    protected String footer_widget_year_value;

    protected BookmarkablePageLink<Void> footer_widget_link;

    protected Label footer_widget_text_field;
    protected String footer_widget_text_value;

    protected boolean compact = false;

    public DefaultFooterWidget(String id) {
        super(id);
    }

    public boolean isCompact() {
        return compact;
    }

    public void setCompact(boolean compact) {
        this.compact = compact;
    }

    @Override
    protected void onInitData() {
        this.footer_widget_version_value = "3.0";
        this.footer_widget_year_value = DateFormatUtils.format(new Date(), "yyyy");
        this.footer_widget_text_value = "Senior Cyber";
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        List<String> htmlClass = new ArrayList<>(2);
        htmlClass.add("main-footer");
        if (this.compact) {
            htmlClass.add("text-sm");
        }
        add(AttributeModifier.replace("class", StringUtils.join(htmlClass, " ")));
        this.footer_widget_version_field = new Label("footer_widget_version_field", new PropertyModel<>(this, "footer_widget_version_value"));
        queue(this.footer_widget_version_field);

        this.footer_widget_year_field = new Label("footer_widget_year_field", new PropertyModel<>(this, "footer_widget_year_value"));
        queue(this.footer_widget_year_field);

        this.footer_widget_link = new BookmarkablePageLink<Void>("footer_widget_link", getApplication().getHomePage());
        queue(this.footer_widget_link);

        this.footer_widget_text_field = new Label("footer_widget_text_field", new PropertyModel<>(this, "footer_widget_text_value"));
        queue(this.footer_widget_text_field);
    }

    @Override
    protected void onComponentTag(final ComponentTag tag) {
        checkComponentTag(tag, "footer");
        super.onComponentTag(tag);
    }

}
