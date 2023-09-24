package com.senior.cyber.frmk.common.wicket.markup.html.panel;

import org.apache.wicket.feedback.IFeedbackMessageFilter;

public class FeedbackPanel extends org.apache.wicket.markup.html.panel.FeedbackPanel {

    private static final long serialVersionUID = 1L;

    public FeedbackPanel(String id) {
        super(id);
    }

    public FeedbackPanel(String id, IFeedbackMessageFilter filter) {
        super(id, filter);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

}
