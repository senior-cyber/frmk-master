package com.senior.cyber.frmk.common.wicket.markup.html;

import org.apache.wicket.util.io.IClusterable;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class FullCalendarProvider implements IClusterable {

    @Serial
    private static final long serialVersionUID = 1L;

    public List<FullCalendarItem> doQuery(Date start, Date end) {
        List<FullCalendarItem> items = query(start, end);
        if (items == null) {
            return new ArrayList<>();
        } else {
            return items;
        }
    }

    public abstract List<FullCalendarItem> query(Date start, Date end);

}
