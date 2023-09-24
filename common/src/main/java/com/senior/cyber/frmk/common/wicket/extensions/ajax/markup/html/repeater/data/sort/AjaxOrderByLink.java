package com.senior.cyber.frmk.common.wicket.extensions.ajax.markup.html.repeater.data.sort;

import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.sort.OrderByLink;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.IAjaxLink;

public abstract class AjaxOrderByLink extends OrderByLink implements IAjaxLink {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param id
     * @param sortProperty
     * @param stateLocator
     */
    public AjaxOrderByLink(final String id, final String sortProperty, final ISortStateLocator stateLocator) {
        super(id, sortProperty, stateLocator);
    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        add(newAjaxEventBehavior("click"));
    }

    /**
     * @param event the name of the default event on which this link will listen to
     * @return the ajax behavior which will be executed when the user clicks the link
     */
    protected AjaxEventBehavior newAjaxEventBehavior(final String event) {
        return new AjaxEventBehavior(event) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onEvent(final AjaxRequestTarget target) {
                onClick();
                AjaxOrderByLink.this.onClick(target);
            }

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                attributes.setPreventDefault(true);

                AjaxOrderByLink.this.updateAjaxAttributes(attributes);
            }
        };
    }

    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
    }

    /**
     * Callback method when an ajax click occurs. All the behavior of changing the sort, etc is
     * already performed before this is called so this method should primarily be used to configure
     * the target.
     *
     * @param target
     */
    @Override
    public abstract void onClick(AjaxRequestTarget target);

}
