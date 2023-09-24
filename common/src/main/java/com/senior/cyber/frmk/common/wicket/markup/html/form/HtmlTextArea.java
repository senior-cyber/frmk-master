package com.senior.cyber.frmk.common.wicket.markup.html.form;

import com.senior.cyber.frmk.common.wicket.resource.CodeMirrorOnDomReady;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

public class HtmlTextArea extends TextArea<String> {

    private static final long serialVersionUID = 1L;

    public HtmlTextArea(String id) {
        super(id);
    }

    public HtmlTextArea(String id, IModel<String> model) {
        super(id, model);
    }

    protected void onInitialize() {
        super.onInitialize();
        this.setOutputMarkupId(true);
    }

    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(new CodeMirrorOnDomReady("CodeMirror.fromTextArea(document.getElementById('" + markupId + "'), { indentUnit: 4, lineNumbers: true, theme: 'night', extraKeys: {'Ctrl-Space': 'autocomplete', 'F11': function(cm) { cm.setOption('fullScreen', !cm.getOption('fullScreen')); }, 'Esc': function(cm) { if (cm.getOption('fullScreen')) cm.setOption('fullScreen', false); }}, mode: {name: 'htmlmixed', globalVars: true} })"));
    }
}