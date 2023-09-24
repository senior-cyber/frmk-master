package com.senior.cyber.frmk.common.wicket.ajax.markup.html.navigation.paging;

import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.IPageable;
import com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging.PagingNavigationIncrementLink;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.IAjaxLink;
import org.apache.wicket.markup.ComponentTag;

public class AjaxPagingNavigationIncrementLink extends PagingNavigationIncrementLink<Void>
        implements
        IAjaxLink {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param id        See Component
     * @param pageable  The pageable component the page links are referring to
     * @param increment increment by
     */
    public AjaxPagingNavigationIncrementLink(final String id, final IPageable pageable,
                                             final int increment) {
        super(id, pageable, increment);

        setOutputMarkupId(true);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(newAjaxPagingNavigationBehavior(pageable, "click"));
    }

    /**
     * @param pageable The pageable component the page links are referring to
     * @param event    the name of the default event on which this link will listen to
     * @return the ajax behavior which will be executed when the user clicks the link
     */
    protected AjaxPagingNavigationBehavior newAjaxPagingNavigationBehavior(IPageable pageable,
                                                                           String event) {
        return new AjaxPagingNavigationBehavior(this, pageable, event) {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                attributes.setPreventDefault(true);
                AjaxPagingNavigationIncrementLink.this.updateAjaxAttributes(attributes);
            }
        };
    }

    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
    }

    /**
     * Fallback event listener, will redisplay the current page.
     *
     * @see org.apache.wicket.markup.html.link.Link#onClick()
     */
    @Override
    public void onClick() {
        onClick(null);
    }

    /**
     * Performs the actual action of this component, performing a non-ajax fallback when there was
     * no AjaxRequestTarget available.
     *
     * @param target the request target, when <code>null</code>, a full page refresh will be generated
     */
    @Override
    public void onClick(AjaxRequestTarget target) {
        // Tell the PageableListView which page to print next
        pageable.setCurrentPage(getPageNumber());
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        // 'onclick' attribute would be set only if this component is attached
        // to HTML element different than 'a'. This 'onclick' will break Ajax's
        // event binding so here we remove it.
        // AjaxFallback is supported only with 'a' HTML element. See WICKET-4862
        tag.remove("onclick");
    }
}
