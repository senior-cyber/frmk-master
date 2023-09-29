package com.senior.cyber.frmk.common.wicket.functional;

import org.apache.wicket.util.io.IClusterable;

@FunctionalInterface
public interface DeserializerFunction<T> extends IClusterable {

    T apply(String value);

}