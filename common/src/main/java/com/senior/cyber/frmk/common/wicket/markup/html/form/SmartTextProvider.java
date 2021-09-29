package com.senior.cyber.frmk.common.wicket.markup.html.form;

import java.io.Serializable;
import java.util.List;

public interface SmartTextProvider extends Serializable {

    List<String> toChoices(String input);

}
