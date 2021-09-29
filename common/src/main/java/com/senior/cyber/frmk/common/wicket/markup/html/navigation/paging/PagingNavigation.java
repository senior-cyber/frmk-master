package com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;

public class PagingNavigation extends org.apache.wicket.markup.html.navigation.paging.PagingNavigation {

    /**
     *
     */
    private static final long serialVersionUID = 7798625371237212895L;

    public PagingNavigation(String id, IPageable pageable) {
        super(id, pageable);
        setOutputMarkupId(true);
    }

    public PagingNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
        setOutputMarkupId(true);
    }

    @Override
    protected void populateItem(LoopItem loopItem) {
        final long pageIndex = getStartIndex() + loopItem.getIndex();
        if (this.pageable.getCurrentPage() == pageIndex) {
            loopItem.add(AttributeModifier.replace("class", "active"));
        } else {
            loopItem.add(AttributeModifier.replace("class", ""));
        }
        super.populateItem(loopItem);
    }
}
