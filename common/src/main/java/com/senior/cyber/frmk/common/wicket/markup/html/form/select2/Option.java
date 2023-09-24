package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String text;

    public Option() {
    }

    public Option(String id, String text) {
        this.id = id;
        this.text = text;
    }

}
