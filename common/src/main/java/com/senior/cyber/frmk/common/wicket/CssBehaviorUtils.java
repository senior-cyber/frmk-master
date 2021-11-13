package com.senior.cyber.frmk.common.wicket;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.parser.XmlTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CssBehaviorUtils {

    public static void appendCssClass(String cssClass, ComponentTag tag) {
        if (tag.getType() != XmlTag.TagType.CLOSE) {
            List<String> errorClasses = new ArrayList<>(Arrays.asList(StringUtils.split(cssClass, " ")));
            List<String> tagClasses = new ArrayList<>(Arrays.asList(StringUtils.split(StringUtils.trim(tag.getAttributes().getString("class", "")), " ")));
            for (String errorClass : errorClasses) {
                if (!tagClasses.contains(errorClass)) {
                    tagClasses.add(errorClass);
                }
            }
            tag.getAttributes().put("class", StringUtils.join(tagClasses, " "));
        }
    }

}