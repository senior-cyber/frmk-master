package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort;

import org.apache.wicket.util.io.IClusterable;

/**
 * @see org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator
 */
public interface ISortStateLocator extends IClusterable {

    /**
     * @return ISortState object
     */
    ISortState getSortState();
    
}
