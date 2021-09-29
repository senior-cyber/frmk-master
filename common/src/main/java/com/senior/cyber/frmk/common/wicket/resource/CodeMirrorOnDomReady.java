package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class CodeMirrorOnDomReady extends OnDomReadyHeaderItem {

    public CodeMirrorOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(CssHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/codemirror.css")));
        dependencies.add(CssHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/addon/hint/show-hint.css")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/codemirror.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/addon/hint/show-hint.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/addon/hint/javascript-hint.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/addon/hint/xml-hint.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/addon/hint/sql-hint.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/addon/hint/html-hint.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/addon/hint/css-hint.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/mode/javascript/javascript.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/mode/sql/sql.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/mode/xml/xml.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/mode/htmlmixed/htmlmixed.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/mode/css/css.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new AdminLTEResourceReference("/plugins/codemirror/mode/markdown/markdown.js")));
        return dependencies;
    }

}
