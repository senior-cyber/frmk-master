package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;

import java.util.List;

public abstract class IMultipleChoiceProvider<Id, Label> extends IChoiceProvider<Id, Label> {

    protected IMultipleChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor
    ) {
        super(idType, idConvertor, labelType, labelConvertor);
    }

    public abstract List<Option> toChoices(List<String> ids);

}
