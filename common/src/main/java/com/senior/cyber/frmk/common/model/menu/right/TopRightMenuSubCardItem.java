package com.senior.cyber.frmk.common.model.menu.right;

import com.senior.cyber.frmk.common.model.Caption;
import com.senior.cyber.frmk.common.model.Emoji;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;

public class TopRightMenuSubCardItem implements TopRightMenu {

    private final ResourceReference avatar;

    private final String title;

    private final String description;

    private final Caption caption;

    private final Emoji emoji;

    private final Class<? extends WebPage> page;

    private final PageParameters parameters;

    public TopRightMenuSubCardItem(ResourceReference avatar, String title, String description, Caption caption, Emoji emoji, Class<? extends WebPage> page) {
        this.avatar = avatar;
        this.title = title;
        this.description = description;
        this.caption = caption;
        this.emoji = emoji;
        this.page = page;
        this.parameters = new PageParameters();
    }

    public TopRightMenuSubCardItem(ResourceReference avatar, String title, String description, Caption caption, Emoji emoji, Class<? extends WebPage> page, PageParameters parameters) {
        this.avatar = avatar;
        this.title = title;
        this.description = description;
        this.caption = caption;
        this.emoji = emoji;
        this.page = page;
        this.parameters = parameters;
    }

    public ResourceReference getAvatar() {
        return avatar;
    }

    public String getTitle() {
        return title;
    }

    public Class<? extends WebPage> getPage() {
        return page;
    }

    public PageParameters getParameters() {
        return parameters;
    }

    public String getDescription() {
        return description;
    }

    public Caption getCaption() {
        return caption;
    }

    public Emoji getEmoji() {
        return emoji;
    }
}
