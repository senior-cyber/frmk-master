package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import com.senior.cyber.frmk.common.wicket.resource.Select2OnDomReady;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.IRequestListener;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Select2MultipleChoice extends FormComponent<Collection<Option>> implements IRequestListener {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Select2MultipleChoice.class);

    private boolean validValue;

    private boolean invalidValue;

    private boolean convertValue;

    private AbstractJdbcChoiceProvider provider;

    private transient Collection<Option> convertedInput;

    private int inputLength;

    public Select2MultipleChoice(String id, IModel<Collection<Option>> object, AbstractJdbcChoiceProvider provider) {
        this(id, object, provider, 0);
    }

    public Select2MultipleChoice(String id, IModel<Collection<Option>> object, AbstractJdbcChoiceProvider provider, int inputLength) {
        super(id, object);
        this.provider = provider;
        this.inputLength = inputLength;
    }

    @Override
    public final FormComponent<Collection<Option>> setType(Class<?> type) {
        throw new UnsupportedOperationException("This class does not support type-conversion because it is performed " + "exclusively by the IChoiceRenderer assigned to this component");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        add(AttributeModifier.replace("multiple", "multiple"));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = getMarkupId(true);
        String url = this.urlForListener(new PageParameters()).toString();
        String theme = "theme:'bootstrap4', ";
        theme = "";
        if (isRequired()) {
            StringResourceModel selectOne = new StringResourceModel("null", this);
//            response.render(new Select2OnDomReady("setTimeout(function() { $('#" + markupId + "')." + "select2({" + theme + " placeholder:'"
//                    + selectOne.getString() + "', ajax: { url: '" + url
//                    + "', dataType: 'json', delay: 250, data: function (params) { return { q: params.term, page: params.page }; }, processResults: function (data, params) { params.page = params.page || 1; return { results: data.items, pagination: { more: data.more } }; }, cache: true }, escapeMarkup: function (markup) { return markup; }, minimumInputLength: "
//                    + this.inputLength + "}); }, 1000)"));
            response.render(new Select2OnDomReady("$('#" + markupId + "')." + "select2({" + theme + " placeholder:'"
                    + selectOne.getString() + "', ajax: { url: '" + url
                    + "', dataType: 'json', delay: 250, data: function (params) { return { q: params.term, page: params.page }; }, processResults: function (data, params) { params.page = params.page || 1; return { results: data.items, pagination: { more: data.more } }; }, cache: true }, escapeMarkup: function (markup) { return markup; }, minimumInputLength: "
                    + this.inputLength + "})"));
        } else {
//            response.render(new Select2OnDomReady("setTimeout(function() { $('#" + markupId + "')."
//                    + "select2({" + theme + " placeholder:'', ajax: { url: '" + url
//                    + "', dataType: 'json', delay: 250, data: function (params) { return { q: params.term, page: params.page }; }, processResults: function (data, params) { params.page = params.page || 1; return { results: data.items, pagination: { more: data.more } }; }, cache: true }, escapeMarkup: function (markup) { return markup; }, minimumInputLength: "
//                    + this.inputLength + "}); }, 1000)"));
            response.render(new Select2OnDomReady("$('#" + markupId + "')."
                    + "select2({" + theme + " placeholder:'', ajax: { url: '" + url
                    + "', dataType: 'json', delay: 250, data: function (params) { return { q: params.term, page: params.page }; }, processResults: function (data, params) { params.page = params.page || 1; return { results: data.items, pagination: { more: data.more } }; }, cache: true }, escapeMarkup: function (markup) { return markup; }, minimumInputLength: "
                    + this.inputLength + "})"));
        }
        // setTimeout(function() { ; }, 1000)
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        checkComponentTag(tag, "select");
        String clazz = tag.getAttribute("class");
        if (clazz == null || clazz.isEmpty()) {
            clazz = "select2";
        } else {
            if (!clazz.contains("select2")) {
                clazz = clazz + " " + "select2";
            }
        }
        tag.put("class", clazz);

        String style = tag.getAttribute("style");
        if (style == null || style.isEmpty()) {
            style = "width: 100%;";
        } else {
            if (!style.contains("width: 100%")) {
                if (style.endsWith(";")) {
                    style = style + "width: 100%;";
                } else {
                    style = style + ";width: 100%;";
                }
            }
        }
        tag.put("style", style);
        super.onComponentTag(tag);
    }

    @Override
    protected Collection<Option> convertValue(String[] value) throws ConversionException {
        this.convertValue = true;
        if (value != null && value.length > 0) {
            this.convertedInput = this.provider.toChoice(Arrays.asList(value));
            return this.convertedInput;
        } else {
            if (this.convertedInput != null) {
                this.convertedInput.clear();
            }
            return null;
        }

    }

    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        if (!this.invalidValue && !this.validValue && !this.convertValue) {
            Collection<Option> choices = getModelObject();
            final AppendingStringBuffer buffer = new AppendingStringBuffer("");
            if (choices != null && !choices.isEmpty()) {
                int i = 0;
                for (Option choice : choices) {
                    i++;
                    appendOptionHtml(buffer, choice, i);
                }
            }
            buffer.append('\n');
            replaceComponentTagBody(markupStream, openTag, buffer);
        } else {
            if (this.invalidValue) {
                if (this.convertValue) {
                    final AppendingStringBuffer buffer = new AppendingStringBuffer("");
                    if (this.convertedInput != null && !this.convertedInput.isEmpty()) {
                        int i = 0;
                        for (Option choice : this.convertedInput) {
                            i++;
                            appendOptionHtml(buffer, choice, i);
                        }
                    }
                    buffer.append('\n');
                    replaceComponentTagBody(markupStream, openTag, buffer);
                }
            }
            if (this.validValue) {
                Collection<Option> choices = getModelObject();
                final AppendingStringBuffer buffer = new AppendingStringBuffer("");
                if (choices != null && !choices.isEmpty()) {
                    int i = 0;
                    for (Option choice : choices) {
                        i++;
                        appendOptionHtml(buffer, choice, i);
                    }
                }
                buffer.append('\n');
                replaceComponentTagBody(markupStream, openTag, buffer);
            }
        }
    }

    protected void appendOptionHtml(AppendingStringBuffer buffer, Option choice, int index) {
        buffer.append("\n<option ");
        setOptionAttributes(buffer, choice, index);
        buffer.append('>');

        String display = choice.getText();

        CharSequence escaped = display;
        if (getEscapeModelStrings()) {
            escaped = escapeOptionHtml(display);
        }

        buffer.append(escaped);
        buffer.append("</option>");
    }

    protected CharSequence escapeOptionHtml(String displayValue) {
        return Strings.escapeMarkup(displayValue);
    }

    protected void setOptionAttributes(AppendingStringBuffer buffer, Option choice, int index) {
        buffer.append("selected=\"selected\" ");
        buffer.append("value=\"");
        buffer.append(Strings.escapeMarkup(choice.getId()));
        buffer.append('"');
    }

    @Override
    public void onRequest() {
        RequestCycle requestCycle = RequestCycle.get();
        Request request = requestCycle.getRequest();
        IRequestParameters params = request.getRequestParameters();

        String term = params.getParameterValue("q").toOptionalString();
        term = StringUtils.trimToEmpty(term);
        int page = params.getParameterValue("page").toInt(1);
        LOGGER.info("onRequest q [{}] page [{}]", term, page);

        List<Option> options = this.provider.searchOption(term, page);

        Select2Response response = new Select2Response();
        response.setMore(this.provider.hasMore(term, page));
        response.setItems(options);

        WebResponse webResponse = (WebResponse) requestCycle.getResponse();
        webResponse.setContentType("application/json");

        OutputStreamWriter stream = new OutputStreamWriter(webResponse.getOutputStream(), getRequest().getCharset());
        try {
            stream.write(toJson(response));
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onValid() {
        super.onValid();
        this.validValue = true;
    }

    @Override
    protected void onInvalid() {
        super.onInvalid();
        this.invalidValue = true;
    }

    @Override
    protected void onAfterRender() {
        super.onAfterRender();
        this.validValue = false;
        this.invalidValue = false;
        this.convertValue = false;
    }

    public int getInputLength() {
        return inputLength;
    }

    protected String toJson(Select2Response response) {
        List<String> items = new ArrayList<>(response.getItems() == null ? 0 : response.getItems().size());
        if (response.getItems() != null) {
            for (Option option : response.getItems()) {
                String id = "\"id\":\"" + (option.getId() == null ? "" : StringUtils.replace(option.getId(), "\"", "\\\"")) + "\"";
                String text = "\"text\":\"" + (option.getText() == null ? "" : StringUtils.replace(option.getText(), "\"", "\\\"")) + "\"";
                items.add("{" + id + "," + text + "}");
            }
        }
        List<String> responseJson = new ArrayList<>(3);
        responseJson.add("\"page\":" + response.getPage());
        responseJson.add("\"more\":" + response.hasMore());
        responseJson.add("\"items\":[" + StringUtils.join(items, ",") + "]");
        return "{" + StringUtils.join(responseJson, ",") + "}";
    }

    @Override
    public boolean rendersPage() {
        return false;
    }

}
