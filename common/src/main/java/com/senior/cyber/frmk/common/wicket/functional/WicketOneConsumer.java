package com.senior.cyber.frmk.common.wicket.functional;

import org.apache.wicket.util.io.IClusterable;

@FunctionalInterface
public interface WicketOneConsumer<Param1> extends IClusterable {

    void accept(Param1 param1);

}
