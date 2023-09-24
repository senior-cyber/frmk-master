package com.senior.cyber.frmk.common.wicket.ajax.markup.html.navigation.paging;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.IAjaxLink;
import org.apache.wicket.markup.ComponentTag;

public class AjaxPagingNavigationBehavior extends AjaxEventBehavior {
    /**
     * For serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The ajaxian link that should receive the event.
     */
    private final IAjaxLink owner;

    /**
     * Attaches the navigation behavior to the owner link and drives the pageable component. The
     * behavior is attached to the markup event.
     *
     * @param owner    the owner ajax link
     * @param pageable the pageable to update
     * @param event    the javascript event to bind to (e.g. onclick)
     */
    public AjaxPagingNavigationBehavior(IAjaxLink owner, IPageable pageable, String event) {
        super(event);
        this.owner = owner;
    }

    /**
     * The ajax event handler. This will execute the event, and update the following components,
     * when present: the navigator the owner link is part of, or when the link is a stand alone
     * component, the link itself. Also the pageable's parent markup container is updated, so its
     * contents can be replaced with the newly generated pageable.
     *
     * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    protected void onEvent(AjaxRequestTarget target) {
        // handle the event
        owner.onClick(target);

        // find the PagingNavigator parent of this link
        AjaxPagingNavigator navigator = ((Component) owner).findParent(AjaxPagingNavigator.class);

        // if this is embedded inside a navigator
        if (navigator != null) {
            // tell the PagingNavigator to update the IPageable
            navigator.onAjaxEvent(target);
        }
    }

    /**
     * @see org.apache.wicket.ajax.AjaxEventBehavior#onComponentTag(org.apache.wicket.markup.ComponentTag)
     */
    @Override
    protected void onComponentTag(ComponentTag tag) {
        if (getComponent().isEnabledInHierarchy()) {
            super.onComponentTag(tag);
        }
    }
}