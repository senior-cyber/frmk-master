package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;

import java.io.Serial;

public abstract class CssAttributeBehavior extends Behavior {

    @Serial
    private static final long serialVersionUID = 1L;

    protected abstract String getCssClass();

    /**
     * @see Behavior#onComponentTag(Component, ComponentTag)
     */
    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        String className = getCssClass();
        if (!Strings.isEmpty(className)) {
            tag.append("class", className, " ");
        }
    }
}