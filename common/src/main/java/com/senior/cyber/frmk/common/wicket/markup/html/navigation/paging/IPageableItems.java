package com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging;

/**
 * @see org.apache.wicket.markup.html.navigation.paging.IPageableItems
 */
public interface IPageableItems extends IPageable {
    /**
     * Gets the total number of items this object has.
     *
     * @return The total number of items this object has.
     */
    int getItemCount();

    /**
     * maximum number of visible items per page
     *
     * @return number of items
     */
    int getItemsPerPage();

    /**
     * set the maximum number of visible items per page
     *
     * @param itemsPerPage
     */
    void setItemsPerPage(int itemsPerPage);

}
