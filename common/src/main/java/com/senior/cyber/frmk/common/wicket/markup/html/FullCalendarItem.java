package com.senior.cyber.frmk.common.wicket.markup.html;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FullCalendarItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3370848291462739218L;

    private Date end;

    private String id;

    private String resourceId;

    private Date start;

    private String title;

    private String borderColor = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));

    private String backgroundColor = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));

    private String textColor = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));

    private String url;

    private boolean allDay = true;

    @Override
    public String toString() {
        List<String> json = new ArrayList<>();
        if (!this.allDay) {
            if (this.end != null) {
                json.add("\"end\":\"" + DateFormatUtils.format(this.end, "yyyy-MM-dd'T'HH:mm:ss") + "\"");
            }
            if (this.start != null) {
                json.add("\"start\":\"" + DateFormatUtils.format(this.start, "yyyy-MM-dd'T'HH:mm:ss") + "\"");
            }
        } else {
            if (this.end != null) {
                json.add("\"end\":\"" + DateFormatUtils.format(this.end, "yyyy-MM-dd") + "\"");
            }
            if (this.start != null) {
                json.add("\"start\":\"" + DateFormatUtils.format(this.start, "yyyy-MM-dd") + "\"");
            }
        }
        if (this.id != null && !"".equals(this.id)) {
            json.add("\"id\":\"" + this.id + "\"");
        }
        if (this.resourceId != null && !"".equals(this.resourceId)) {
            json.add("\"resourceId\":\"" + this.resourceId + "\"");
        }
        if (this.title != null && !"".equals(this.title)) {
            json.add("\"title\":\"" + this.title + "\"");
        }
        if (this.borderColor != null && !"".equals(this.borderColor)) {
            json.add("\"borderColor\":\"" + this.borderColor + "\"");
        }
        if (this.backgroundColor != null && !"".equals(this.backgroundColor)) {
            json.add("\"backgroundColor\":\"" + this.backgroundColor + "\"");
        }
        if (this.url != null && !"".equals(this.url)) {
            json.add("\"url\":\"" + this.url + "\"");
        }
        if (this.textColor != null && !"".equals(this.textColor)) {
            json.add("\"textColor\":\"" + this.textColor + "\"");
        }
        json.add("\"allDay\":" + this.allDay);
        return "{" + StringUtils.join(json, ",") + "}";
    }


    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

}
