package com.senior.cyber.frmk.common.wicket.functional;

import org.apache.wicket.util.io.IClusterable;

@FunctionalInterface
public interface WicketFiveConsumer<Param1, Param2, Param3, Param4, Param5> extends IClusterable {

    void accept(Param1 param1, Param2 param2, Param3 param3, Param4 param4, Param5 param5);

}