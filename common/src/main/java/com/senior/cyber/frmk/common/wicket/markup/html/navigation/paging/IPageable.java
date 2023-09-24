package com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging;

import org.apache.wicket.util.io.IClusterable;

/**
 * @see org.apache.wicket.markup.html.navigation.paging.IPageable
 */
public interface IPageable extends IClusterable {
    /**
     * @return The current page that is or will be rendered (page number is zero-based)
     */
    int getCurrentPage();

    /**
     * Sets the a page that should be rendered (page number is zero-based)
     *
     * @param page The page that should be rendered.
     */
    void setCurrentPage(int page);

    /**
     * Gets the total number of pages this pageable object has.
     *
     * @return The total number of pages this pageable object has
     */
    int getPageCount();
}