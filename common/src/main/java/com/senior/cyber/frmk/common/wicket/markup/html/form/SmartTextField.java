package com.senior.cyber.frmk.common.wicket.markup.html.form;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.DefaultCssAutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SmartTextField extends DefaultCssAutoCompleteTextField<String> {

    @Serial
    private static final long serialVersionUID = 1L;

    private SmartTextProvider provider;

    private int inputLength = 1;

    public SmartTextField(String id, IModel<String> model, SmartTextProvider provider) {
        this(id, model, provider, 1);
    }

    public SmartTextField(String id, IModel<String> model, SmartTextProvider provider, int inputLength) {
        super(id, model);
        this.inputLength = inputLength;
        this.provider = provider;
    }

    @Override
    protected AutoCompleteBehavior<String> newAutoCompleteBehavior(IAutoCompleteRenderer<String> renderer, AutoCompleteSettings settings) {
        settings.setShowListOnEmptyInput(false);
        settings.setUseSmartPositioning(true);
        settings.setMinInputLength(this.inputLength);
        return super.newAutoCompleteBehavior(renderer, settings);
    }

    @Override
    protected Iterator<String> getChoices(String input) {
        if (this.provider != null) {
            List<String> choices = this.provider.toChoices(input);
            if (choices != null) {
                return choices.listIterator();
            } else {
                return Collections.emptyIterator();
            }
        } else {
            return Collections.emptyIterator();
        }
    }

}
