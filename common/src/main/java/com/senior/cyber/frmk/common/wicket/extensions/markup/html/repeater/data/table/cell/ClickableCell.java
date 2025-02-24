package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.cell;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.senior.cyber.frmk.common.wicket.functional.WicketTwoConsumer;
import com.senior.cyber.frmk.common.wicket.widget.ReadOnlyView;
import jakarta.persistence.Tuple;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ClickableCell extends ItemPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private WicketTwoConsumer<Tuple, Link<Void>> onClick;

    private IModel<?> label;

    private Tuple model;

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, byte v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, byte v, String pattern) {
        this(click, model, Model.of(ReadOnlyView.getFormat(pattern).format(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Byte v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Byte v, String pattern) {
        this(click, model, v != null ? Model.of(ReadOnlyView.getFormat(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, boolean v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Boolean v) {
        this(click, model, v != null && v ? Model.of("Yes") : Model.of("No"));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, short v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, short v, String pattern) {
        this(click, model, Model.of(ReadOnlyView.getFormat(pattern).format(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Short v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Short v, String pattern) {
        this(click, model, v != null ? Model.of(ReadOnlyView.getFormat(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, int v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, int v, String pattern) {
        this(click, model, Model.of(ReadOnlyView.getFormat(pattern).format(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Integer v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Integer v, String pattern) {
        this(click, model, v != null ? Model.of(ReadOnlyView.getFormat(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, long v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, long v, String pattern) {
        this(click, model, Model.of(ReadOnlyView.getFormat(pattern).format(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Long v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Long v, String pattern) {
        this(click, model, v != null ? Model.of(ReadOnlyView.getFormat(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, double v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, double v, String pattern) {
        this(click, model, Model.of(ReadOnlyView.getFormat(pattern).format(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Double v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Double v, String pattern) {
        this(click, model, v != null ? Model.of(ReadOnlyView.getFormat(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, float v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, float v, String pattern) {
        this(click, model, Model.of(ReadOnlyView.getFormat(pattern).format(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Float v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Float v, String pattern) {
        this(click, model, v != null ? Model.of(ReadOnlyView.getFormat(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, String v) {
        this(click, model, v != null ? Model.of(v) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Date v, String pattern) {
        this(click, model, v != null ? Model.of(DateFormatUtils.format(v, pattern)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, BigDecimal v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, BigDecimal v, String pattern) {
        this(click, model, v != null ? Model.of(ReadOnlyView.getFormat(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, BigInteger v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, BigInteger v, String pattern) {
        this(click, model, v != null ? Model.of(ReadOnlyView.getFormat(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Character v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Number v) {
        this(click, model, v != null ? Model.of(String.valueOf(v.doubleValue())) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, Number v, String pattern) {
        this(click, model, v != null ? Model.of(ReadOnlyView.getFormat(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, char v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketTwoConsumer<Tuple, Link<Void>> click, Tuple model, IModel<?> v) {
        super(v);
        this.onClick = click;
        this.label = v;
        this.model = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Link<Void> link = new Link<Void>("link") {

            /**
             *
             */
            private static final long serialVersionUID = 7743706733174627847L;

            @Override
            public void onClick() {
                onClick.accept(model, this);
            }

        };
        add(link);
        Label text = new Label("text", this.label);
        link.add(text);
    }
}
