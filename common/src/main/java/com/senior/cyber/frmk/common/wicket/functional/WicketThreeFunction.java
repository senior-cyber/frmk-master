package com.senior.cyber.frmk.common.wicket.functional;

import org.apache.wicket.util.io.IClusterable;

@FunctionalInterface
public interface WicketThreeFunction<Param1, Param2, Param3, Return> extends IClusterable {

    Return apply(Param1 param1, Param2 param2, Param3 param3);

}
