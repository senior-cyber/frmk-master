package com.senior.cyber.frmk.common.wicket.functional;

import org.apache.wicket.util.io.IClusterable;

@FunctionalInterface
public interface WicketTwoConsumer<Param1, Param2> extends IClusterable {

    void accept(Param1 param1, Param2 param2);

}