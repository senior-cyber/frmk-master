package com.senior.cyber.frmk.common.wicket.ajax.markup.html.navigation.paging;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageable;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.link.Link;

public class AjaxPagingNavigation extends PagingNavigation {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param id       See Component
     * @param pageable The underlying pageable component to navigate
     */
    public AjaxPagingNavigation(final String id, final IPageable pageable) {
        this(id, pageable, null);
    }

    /**
     * Constructor.
     *
     * @param id            See Component
     * @param pageable      The underlying pageable component to navigate
     * @param labelProvider The label provider for the text that the links should be displaying.
     */
    public AjaxPagingNavigation(final String id, final IPageable pageable,
                                final IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
    }

    /**
     * Factory method for creating ajaxian page number links.
     *
     * @param id        link id
     * @param pageable  the pageable
     * @param pageIndex the index the link points to
     * @return the ajaxified page number link.
     */
    @Override
    protected Link<?> newPagingNavigationLink(String id, IPageable pageable, int pageIndex) {
        return new AjaxPagingNavigationLink(id, pageable, pageIndex);
    }
}
