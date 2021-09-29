package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

public class TextField<T> extends org.apache.wicket.markup.html.form.TextField<Expression<T>> {

    /**
     *
     */
    private static final long serialVersionUID = -1904239188848660439L;

    protected final ExpressionConverter<T> converter;

    public TextField(String id, ExpressionConverter<T> converter) {
        super(id);
        setType(Expression.class);
        this.converter = converter;
    }

    public TextField(String id, IModel<Expression<T>> model, ExpressionConverter<T> converter) {
        super(id, model);
        setType(Expression.class);
        this.converter = converter;
    }

    @Override
    protected IConverter<?> createConverter(Class<?> type) {
        return this.converter;
    }

}
