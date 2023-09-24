package com.senior.cyber.frmk.common.wicket.functional;

import org.apache.wicket.util.io.IClusterable;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface FilterFunction<T> extends IClusterable {

    List<String> apply(Boolean count, String key, Map<String, String> alias, Map<String, Object> param, String filterText, DeserializerFunction<T> deserializer);

}
