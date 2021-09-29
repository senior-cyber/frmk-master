package com.senior.cyber.frmk.common.wicket.functional;

import org.apache.wicket.util.io.IClusterable;

@FunctionalInterface
public interface WicketOneFunction<Param1, Return> extends IClusterable {

    Return apply(Param1 param1);

}
