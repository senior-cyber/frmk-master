package com.senior.cyber.frmk.common.wicket.markup.html.form.validation;

import com.senior.cyber.frmk.common.wicket.functional.WicketOneConsumer;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;

import java.io.Serial;

public class LamdaFormValidator implements org.apache.wicket.markup.html.form.validation.IFormValidator {

    @Serial
    private static final long serialVersionUID = 1L;

    protected FormComponent<?>[] components;

    protected WicketOneConsumer<Form<?>> validator;

    public LamdaFormValidator(WicketOneConsumer<Form<?>> validator, FormComponent<?> component) {
        if (component == null) {
            throw new WicketRuntimeException("component is required");
        }
        this.components = new FormComponent<?>[]{component};
        this.validator = validator;
    }

    public LamdaFormValidator(WicketOneConsumer<Form<?>> validator, FormComponent<?>... components) {
        if (components == null || components.length == 0) {
            throw new WicketRuntimeException("components is required");
        }
        this.components = components;
        this.validator = validator;
    }

    @Override
    public FormComponent<?>[] getDependentFormComponents() {
        return this.components;
    }

    @Override
    public void validate(org.apache.wicket.markup.html.form.Form<?> form) {
        if (this.validator != null) {
            this.validator.accept(form);
        }
    }

}
