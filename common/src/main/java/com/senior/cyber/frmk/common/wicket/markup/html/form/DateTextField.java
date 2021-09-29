package com.senior.cyber.frmk.common.wicket.markup.html.form;

import com.senior.cyber.frmk.common.wicket.resource.DatePickerOnDomReady;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;

import java.util.Date;

public class DateTextField extends org.apache.wicket.extensions.markup.html.form.DateTextField {

    /**
     *
     */
    private static final long serialVersionUID = 3349883874886789180L;

    private static final String JAVA_PATTERN = "dd/MM/yyyy";
    private static final String JAVASCRIPT_PATTERN = "DD/MM/YYYY";

    public DateTextField(String id, IModel<Date> model) {
        super(id, model, JAVA_PATTERN);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        add(AttributeModifier.replace("data-toggle", "datetimepicker"),
                AttributeModifier.replace("data-target", "#" + getMarkupId(true)));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(new DatePickerOnDomReady("$('#" + markupId + "').datetimepicker({'tooltips': { 'today': 'Today', 'close': 'Close'}, 'buttons': { 'showToday': true, 'showClear': false, 'showClose': true }, 'format':'" + JAVASCRIPT_PATTERN + "'})"));
    }

}
