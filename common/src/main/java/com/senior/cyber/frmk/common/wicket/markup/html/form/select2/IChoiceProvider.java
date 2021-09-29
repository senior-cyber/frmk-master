package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.convertor.Convertor;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class IChoiceProvider<Id, Label> implements IChoiceRenderer<Option>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7442787470313354188L;

    public static int LIMIT = 10;

    protected Class<Id> idType;

    protected Class<Label> labelType;

    protected Convertor<Id> idConvertor;
    protected Convertor<Label> labelConvertor;

    protected IChoiceProvider(
            Class<Id> idType, Convertor<Id> idConvertor,
            Class<Label> labelType, Convertor<Label> labelConvertor
    ) {
        this.idType = idType;
        this.idConvertor = idConvertor;
        this.labelType = labelType;
        this.labelConvertor = labelConvertor;
    }

    public abstract List<Option> query(String term, int page);

    public abstract boolean hasMore(String term, int page);

    public final List<Option> doQuery(String term, int page) {
        List<Option> options = query(term, page);
        if (options == null) {
            return new ArrayList<>();
        } else {
            return options;
        }
    }

}
