package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.tree;

import com.senior.cyber.frmk.common.wicket.functional.WicketTwoFunction;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;

import java.util.Set;

public class NestedTree<T> extends DefaultNestedTree<T> {

    /**
     *
     */
    private static final long serialVersionUID = 2336213290953236724L;

    private WicketTwoFunction<String, IModel<T>, Component> labelFactory;

    private WicketTwoFunction<String, IModel<T>, MarkupContainer> linkFactory;

    public NestedTree(String id, ITreeProvider<T> provider, WicketTwoFunction<String, IModel<T>, Component> labelFactory,
                      WicketTwoFunction<String, IModel<T>, MarkupContainer> linkFactory) {
        super(id, provider);
        this.labelFactory = labelFactory;
        this.linkFactory = linkFactory;
    }

    public NestedTree(String id, ITreeProvider<T> provider, IModel<? extends Set<T>> state,
                      WicketTwoFunction<String, IModel<T>, Component> labelFactory,
                      WicketTwoFunction<String, IModel<T>, MarkupContainer> linkFactory) {
        super(id, provider, state);
        this.labelFactory = labelFactory;
        this.linkFactory = linkFactory;
    }

    @Override
    protected Component newContentComponent(String id, IModel<T> node) {
        return new Folder(id, this, node);
    }

    public class Folder extends org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder<T> {

        /**
         *
         */
        private static final long serialVersionUID = -2261932935927139709L;

        public Folder(String id, AbstractTree<T> tree, IModel<T> model) {
            super(id, tree, model);
        }

        @Override
        protected MarkupContainer newLinkComponent(String id, IModel<T> model) {
            if (linkFactory == null) {
                return super.newLinkComponent(id, model);
            } else {
                MarkupContainer container = linkFactory.apply(id, model);
                if (container == null) {
                    return super.newLinkComponent(id, model);
                } else {
                    return container;
                }
            }
        }

        @Override
        protected Component newLabelComponent(String id, IModel<T> model) {
            if (labelFactory == null) {
                return super.newLabelComponent(id, model);
            } else {
                return labelFactory.apply(id, model);
            }
        }

    }

}
