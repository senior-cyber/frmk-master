package com.senior.cyber.frmk.common.wicket.functional;

import org.apache.wicket.util.io.IClusterable;

@FunctionalInterface
public interface SerializerFunction<T> extends IClusterable {

    String apply(String key, T value);

}