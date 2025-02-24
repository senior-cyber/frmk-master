package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CodeMirrorOnDomReady extends OnDomReadyHeaderItem {

    public CodeMirrorOnDomReady(CharSequence javaScript) {
        super(javaScript);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();
        List<HeaderItem> dependencies = new ArrayList<>(0);
        dependencies.add(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/codemirror.css").getPath())));
        dependencies.add(CssHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/addon/hint/show-hint.css").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/codemirror.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/addon/hint/show-hint.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/addon/hint/javascript-hint.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/addon/hint/xml-hint.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/addon/hint/sql-hint.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/addon/hint/html-hint.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/addon/hint/css-hint.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/mode/javascript/javascript.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/mode/sql/sql.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/mode/xml/xml.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/mode/htmlmixed/htmlmixed.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/mode/css/css.js").getPath())));
        dependencies.add(JavaScriptHeaderItem.forReference(new FileSystemResourceReference(new File(adminLte, "/plugins/codemirror/mode/markdown/markdown.js").getPath())));
        return dependencies;
    }

}
