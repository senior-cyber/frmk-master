package com.senior.cyber.frmk.common.wicket.layout;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class ModalDialog extends Panel {

    private static final long serialVersionUID = 1L;

    private static final String DIALOG_ID = "dialog";

    /**
     * The id for the content of this dialog.
     *
     * @see #setContent(Component)
     * @see #open(Component, AjaxRequestTarget)
     */
    public static final String CONTENT_ID = "content";

    protected final WebMarkupContainer dialog;

    protected String htmlClass = DialogSizeEnum.Large.getLiteral();

    protected DialogSizeEnum size = DialogSizeEnum.Large;

    public ModalDialog(String id) {
        super(id);

        setOutputMarkupId(true);

        this.dialog = new WebMarkupContainer(DIALOG_ID);
        this.dialog.setOutputMarkupId(true);
        this.dialog.setVisible(false);
        this.dialog.add(AttributeModifier.replace("class", new PropertyModel<>(this, "htmlClass")));

        add(this.dialog);
    }

    public DialogSizeEnum getSize() {
        return size;
    }

    public void setSize(DialogSizeEnum size) {
        this.size = size;
        this.htmlClass = this.size.getLiteral();
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        if (isOpen()) {
            tag.put("class", "modal fade show");
            tag.put("style", "display: block; padding-right: 17px;");
            tag.put("aria-modal", "true");
            tag.put("role", "dialog");
        } else {
            tag.put("class", "modal fade");
        }
        super.onComponentTag(tag);
    }

    public void setContent(Component content) {
        if (!CONTENT_ID.equals(content.getId())) {
            throw new IllegalArgumentException(
                    "Content must have wicket id set to ModalDialog.CONTENT_ID");
        }
        this.dialog.addOrReplace(content);
    }

    public ModalDialog open(Component content, AjaxRequestTarget target) {
        setContent(content);
        this.dialog.setVisible(true);
        if (target != null) {
            target.add(this);
            MasterPage page = (MasterPage) getPage();
            target.prependJavaScript("$('#" + page.bodyWicketId + "').addClass('modal-open')");
            target.prependJavaScript("$('#" + page.bodyWicketId + "').attr('style','height: auto; padding-right: 17px;')");
            target.prependJavaScript("$('#" + page.overlayWicketId + "').addClass('modal-backdrop fade show')");
        }
        return this;
    }

    public ModalDialog open(AjaxRequestTarget target) {
        this.dialog.setVisible(true);
        if (target != null) {
            target.add(this);
            MasterPage page = (MasterPage) getPage();
            target.prependJavaScript("$('#" + page.bodyWicketId + "').addClass('modal-open')");
            target.prependJavaScript("$('#" + page.bodyWicketId + "').attr('style','height: auto; padding-right: 17px;')");
            target.prependJavaScript("$('#" + page.overlayWicketId + "').addClass('modal-backdrop fade show')");
        }
        return this;
    }

    public boolean isOpen() {
        return this.dialog.isVisible();
    }

    public ModalDialog close(AjaxRequestTarget target) {
        this.dialog.setVisible(false);
        this.dialog.removeAll();
        if (target != null) {
            target.add(this);
            MasterPage page = (MasterPage) getPage();
            target.prependJavaScript("$('#" + page.bodyWicketId + "').removeClass('modal-open')");
            target.prependJavaScript("$('#" + page.bodyWicketId + "').attr('style','height: auto;')");
            target.prependJavaScript("$('#" + page.overlayWicketId + "').removeClass('modal-backdrop fade show')");
        }
        return this;
    }
}