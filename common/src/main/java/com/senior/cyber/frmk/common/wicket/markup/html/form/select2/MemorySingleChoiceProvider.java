package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemorySingleChoiceProvider<Id, Label> extends ISingleChoiceProvider<Id, Label> {

    private final List<Option> options;

    public MemorySingleChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor,
            Option option) {
        super(idType, idConvertor, labelType, labelConvertor);
        this.options = Arrays.asList(option);
    }

    public MemorySingleChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor,
            Option... options) {
        super(idType, idConvertor, labelType, labelConvertor);
        this.options = Arrays.asList(options);
    }

    public MemorySingleChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor,
            List<Option> options) {
        super(idType, idConvertor, labelType, labelConvertor);
        this.options = options;
    }

    @Override
    public Option toChoice(String id) {
        if (this.options == null) {
            return null;
        }
        for (Option option : this.options) {
            if (option.getId().equals(id)) {
                return option;
            }
        }
        return null;
    }

    @Override
    public List<Option> query(String search, int i) {
        if (search == null || "".equals(search) || this.options == null) {
            return null;
        }
        List<Option> results = new ArrayList<>();
        for (Option option : this.options) {
            if (StringUtils.upperCase(option.getText()).startsWith(StringUtils.upperCase(search))) {
                results.add(option);
            }
        }
        return results;
    }

    @Override
    public boolean hasMore(String s, int i) {
        return false;
    }

    @Override
    public Object getDisplayValue(Option object) {
        return object.getText();
    }

    @Override
    public String getIdValue(Option object, int index) {
        return object.getId();
    }

    @Override
    public Option getObject(String id, IModel<? extends List<? extends Option>> choices) {
        if (id == null || "".equals(id) || this.options == null) {
            return null;
        }
        for (Option option : this.options) {
            if (option.getId().equals(id)) {
                return option;
            }
        }
        return null;
    }

}
