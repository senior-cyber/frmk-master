package com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging;

import org.apache.wicket.util.io.IClusterable;

/**
 * @see org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider
 */
public interface IPagingLabelProvider extends IClusterable {

    /**
     * @param page The page number for which the label must be generated.
     * @return The string to be displayed for this page number
     */
    String getPageLabel(int page);
    
}