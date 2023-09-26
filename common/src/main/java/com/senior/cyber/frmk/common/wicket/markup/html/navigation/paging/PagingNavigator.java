package com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;

import java.io.Serial;

/**
 * @see org.apache.wicket.markup.html.navigation.paging.PagingNavigator
 */
public class PagingNavigator extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The navigation bar to be printed, e.g. 1 | 2 | 3 etc.
     */
    private PagingNavigation pagingNavigation;
    private final IPageable pageable;
    private final IPagingLabelProvider labelProvider;

    /**
     * Constructor.
     *
     * @param id       See Component
     * @param pageable The pageable component the page links are referring to.
     */
    public PagingNavigator(final String id, final IPageable pageable) {
        this(id, pageable, null);
        setOutputMarkupId(true);
    }

    /**
     * Constructor.
     *
     * @param id            See Component
     * @param pageable      The pageable component the page links are referring to.
     * @param labelProvider The label provider for the link text.
     */
    public PagingNavigator(final String id, final IPageable pageable, final IPagingLabelProvider labelProvider) {
        super(id);
        this.pageable = pageable;
        this.labelProvider = labelProvider;
        setOutputMarkupId(true);
    }


    /**
     * {@link IPageable} this navigator is linked with
     *
     * @return {@link IPageable} instance
     */
    public final IPageable getPageable() {
        return pageable;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // Get the navigation bar and add it to the hierarchy
        pagingNavigation = newNavigation("navigation", pageable, labelProvider);
        add(pagingNavigation);

        // Add additional page links
        add(newPagingNavigationLink("first", pageable, 0).add(new TitleAppender("PagingNavigator.first")));
        add(newPagingNavigationIncrementLink("prev", pageable, -1).add(new TitleAppender("PagingNavigator.previous")));
        add(newPagingNavigationIncrementLink("next", pageable, 1).add(new TitleAppender("PagingNavigator.next")));
        add(newPagingNavigationLink("last", pageable, -1).add(new TitleAppender("PagingNavigator.last")));
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
    protected AbstractLink newPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
        return new PagingNavigationIncrementLink<Void>(id, pageable, increment);
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
    protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
        return new PagingNavigationLink<Void>(id, pageable, pageNumber);
    }

    /**
     * Create a new PagingNavigation. May be subclassed to make us of specialized PagingNavigation.
     *
     * @param id            The id of the navigation component
     * @param pageable      the pageable component
     * @param labelProvider The label provider for the link text.
     * @return the navigation object
     */
    protected PagingNavigation newNavigation(final String id, final IPageable pageable, final IPagingLabelProvider labelProvider) {
        return new PagingNavigation(id, pageable, labelProvider);
    }

    /**
     * Gets the pageable navigation component for configuration purposes.
     *
     * @return the associated pageable navigation.
     */
    public final PagingNavigation getPagingNavigation() {
        return pagingNavigation;
    }

    /**
     * Appends title attribute to navigation links
     *
     * @author igor.vaynberg
     */
    private final class TitleAppender extends Behavior {

        @Serial
        private static final long serialVersionUID = 1L;

        private final String resourceKey;

        /**
         * Constructor
         *
         * @param resourceKey resource key of the message
         */
        public TitleAppender(String resourceKey) {
            this.resourceKey = resourceKey;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onComponentTag(Component component, ComponentTag tag) {
            tag.put("title", PagingNavigator.this.getString(resourceKey));
        }
    }

}