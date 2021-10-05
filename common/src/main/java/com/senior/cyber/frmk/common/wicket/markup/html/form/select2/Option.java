package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

import java.io.Serializable;

public class Option implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8984541544744301572L;

    private String id;

    private String text;

    public static final ChoiceRenderer RENDERER = new ChoiceRenderer();

    public Option() {
    }

    public Option(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Option other = (Option) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

    private static class ChoiceRenderer implements IChoiceRenderer<Option> {

        @Override
        public Object getDisplayValue(Option object) {
            return object.getText();
        }

        @Override
        public String getIdValue(Option object, int index) {
            return object.getId();
        }

    }

}
