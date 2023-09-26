package com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.DisabledAttributeLinkBehavior;
import org.apache.wicket.markup.html.link.Link;

import java.io.Serial;

/**
 * @see org.apache.wicket.markup.html.navigation.paging.PagingNavigationIncrementLink
 */
public class PagingNavigationIncrementLink<T> extends Link<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The increment.
     */
    private final int increment;

    /**
     * The PageableListView the page links are referring to.
     */
    protected final IPageable pageable;

    /**
     * Constructor.
     *
     * @param id        See Component
     * @param pageable  The pageable component the page links are referring to
     * @param increment increment by
     */
    public PagingNavigationIncrementLink(final String id, final IPageable pageable,
                                         final int increment) {
        super(id);
        setAutoEnable(true);
        this.increment = increment;
        this.pageable = pageable;

        add(new DisabledAttributeLinkBehavior());
    }

    /**
     * @see org.apache.wicket.markup.html.link.Link#onClick()
     */
    @Override
    public void onClick() {
        // Tell the PageableListView which page to print next
        pageable.setCurrentPage(getPageNumber());

        // Return the current page.
        setResponsePage(getPage());
    }

    /**
     * Determines the next page number for the pageable component.
     *
     * @return the new page number
     */
    public final int getPageNumber() {
        // Determine the page number based on the current
        // PageableListView page and the increment
        int idx = pageable.getCurrentPage() + increment;

        // make sure the index lies between 0 and the last page
        return Math.max(0, Math.min(pageable.getPageCount() - 1, idx));
    }

    /**
     * @return True if it is referring to the first page of the underlying PageableListView.
     */
    public boolean isFirst() {
        return pageable.getCurrentPage() <= 0;
    }

    /**
     * @return True if it is referring to the last page of the underlying PageableListView.
     */
    public boolean isLast() {
        return pageable.getCurrentPage() >= (pageable.getPageCount() - 1);
    }

    /**
     * Returns true if the page link links to the given page.
     *
     * @param page ignored
     * @return True if this link links to the given page
     * @see org.apache.wicket.markup.html.link.BookmarkablePageLink#linksTo(org.apache.wicket.Page)
     */
    @Override
    public boolean linksTo(final Page page) {
        pageable.getCurrentPage();
        return ((increment < 0) && isFirst()) || ((increment > 0) && isLast());
    }

}