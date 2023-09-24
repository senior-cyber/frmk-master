package com.senior.cyber.frmk.common.wicket.ajax.markup.html.navigation.paging;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageable;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.PagingNavigation;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.repeater.AbstractRepeater;

public class AjaxPagingNavigator extends PagingNavigator {
    private static final long serialVersionUID = 1L;

    /**
     * The pageable component that needs to be updated.
     */
    private final IPageable pageable;

    /**
     * Constructor.
     *
     * @param id       See Component
     * @param pageable The pageable component the page links are referring to.
     */
    public AjaxPagingNavigator(final String id, final IPageable pageable) {
        this(id, pageable, null);
    }

    /**
     * Constructor.
     *
     * @param id            See Component
     * @param pageable      The pageable component the page links are referring to.
     * @param labelProvider The label provider for the link text.
     */
    public AjaxPagingNavigator(final String id, final IPageable pageable,
                               final IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
        this.pageable = pageable;
        setOutputMarkupId(true);
    }

    /**
     * Create a new increment link. May be subclassed to make use of specialized links, e.g. Ajaxian
     * links.
     *
     * @param id        the link id
     * @param pageable  the pageable to control
     * @param increment the increment
     * @return the increment link
     */
    @Override
    protected AbstractLink newPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
        return new AjaxPagingNavigationIncrementLink(id, pageable, increment);
    }

    /**
     * Create a new pagenumber link. May be subclassed to make use of specialized links, e.g.
     * Ajaxian links.
     *
     * @param id         the link id
     * @param pageable   the pageable to control
     * @param pageNumber the page to jump to
     * @return the pagenumber link
     */
    @Override
    protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
        return new AjaxPagingNavigationLink(id, pageable, pageNumber);
    }

    /**
     * @see org.apache.wicket.markup.html.navigation.paging.PagingNavigator#newNavigation(java.lang.String,
     * org.apache.wicket.markup.html.navigation.paging.IPageable,
     * org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider)
     */
    @Override
    protected PagingNavigation newNavigation(final String id, final IPageable pageable,
                                             final IPagingLabelProvider labelProvider) {
        return new AjaxPagingNavigation(id, pageable, labelProvider);
    }

    /**
     * Override this method to specify the markup container where your IPageable is part of. This
     * default implementation tries to find a parent which is not an {@link AbstractRepeater} and outputs
     * its markup id. This is necessary as ListViews can't be updated themselves.
     *
     * @param target the request target to add the components that need to be updated in the ajax
     *               event.
     * @see Component#getOutputMarkupId()
     */
    protected void onAjaxEvent(AjaxRequestTarget target) {
        // Update a parental container of the pageable, this assumes that the pageable is a component.
        Component container = ((Component) pageable);
        while (container instanceof AbstractRepeater || container.getOutputMarkupId() == false) {
            Component parent = container.getParent();
            if (parent == null) {
                break;
            }
            container = parent;
        }
        target.add(container);

        // in case the navigator is not contained by the container, we have
        // to add it to the response
        if (((MarkupContainer) container).contains(this, true) == false) {
            target.add(this);
        }
    }
}
