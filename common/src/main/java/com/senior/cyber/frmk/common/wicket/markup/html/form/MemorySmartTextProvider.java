package com.senior.cyber.frmk.common.wicket.markup.html.form;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MemorySmartTextProvider implements SmartTextProvider {

    private final List<String> choices;

    public MemorySmartTextProvider(List<String> choices) {
        this.choices = choices;
    }

    @Override
    public List<String> toChoices(String input) {
        if (input == null || "".equals(input) || this.choices == null) {
            return null;
        }
        List<String> results = new ArrayList<>();
        for (String choice : this.choices) {
            if (StringUtils.upperCase(choice).startsWith(StringUtils.upperCase(input))) {
                results.add(choice);
            }
        }
        return results;
    }

}
