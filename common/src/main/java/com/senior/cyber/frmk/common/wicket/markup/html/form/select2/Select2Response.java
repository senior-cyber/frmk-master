package com.senior.cyber.frmk.common.wicket.markup.html.form.select2;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Select2Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int page = 1;

    private boolean more = true;

    private List<Option> items = new ArrayList<>();

    public boolean hasMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public List<Option> getItems() {
        return items;
    }

    public void setItems(List<Option> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
