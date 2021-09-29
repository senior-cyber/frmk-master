package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;

public abstract class ISingleChoiceProvider<Id, Label> extends IChoiceProvider<Id, Label> {

    protected ISingleChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor
    ) {
        super(idType, idConvertor, labelType, labelConvertor);
    }

    public abstract Option toChoice(String id);

}
