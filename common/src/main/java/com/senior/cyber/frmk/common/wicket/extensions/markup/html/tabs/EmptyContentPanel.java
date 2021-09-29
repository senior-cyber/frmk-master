package com.senior.cyber.frmk.common.wicket.extensions.markup.html.tabs;

import com.senior.cyber.frmk.common.wicket.layout.Size;
import com.senior.cyber.frmk.common.wicket.layout.UIColumn;
import com.senior.cyber.frmk.common.wicket.layout.UIRow;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;

import java.util.Map;

public class EmptyContentPanel extends ContentPanel {

    protected UIRow row;

    protected UIColumn column;

    protected Map<String, Object> data;

    public EmptyContentPanel(String id, String title, TabbedPanel<Tab> containerPanel, Map<String, Object> data) {
        super(id, title, containerPanel, data);
    }

    @Override
    protected void onInitData() {
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.row = UIRow.newUIRow("row", body);
        this.column = this.row.newUIColumn("column", Size.Twelve_12);
    }

}