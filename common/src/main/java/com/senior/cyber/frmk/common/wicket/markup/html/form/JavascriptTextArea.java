package com.senior.cyber.frmk.common.wicket.markup.html.form;

import com.senior.cyber.frmk.common.wicket.resource.CodeMirrorOnDomReady;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

public class JavascriptTextArea extends TextArea<String> {

    /**
     *
     */
    private static final long serialVersionUID = 7590641085407244298L;

    public JavascriptTextArea(String id) {
        super(id);
    }

    public JavascriptTextArea(String id, IModel<String> model) {
        super(id, model);
    }

    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(new CodeMirrorOnDomReady("CodeMirror.fromTextArea(document.getElementById('" + markupId + "'), { indentUnit: 4, lineNumbers: true, theme: 'night', extraKeys: {'Ctrl-Space': 'autocomplete', 'F11': function(cm) { cm.setOption('fullScreen', !cm.getOption('fullScreen')); }, 'Esc': function(cm) { if (cm.getOption('fullScreen')) cm.setOption('fullScreen', false); }}, mode: {name: 'javascript', globalVars: true} })"));
    }

    protected void onInitialize() {
        super.onInitialize();
        this.setOutputMarkupId(true);
    }
}
