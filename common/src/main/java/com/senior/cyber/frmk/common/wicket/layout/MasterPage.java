package com.senior.cyber.frmk.common.wicket.layout;

import com.senior.cyber.frmk.common.Pkg;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import com.senior.cyber.frmk.common.model.*;
import com.senior.cyber.frmk.common.model.menu.left.*;
import com.senior.cyber.frmk.common.model.menu.right.*;
import com.senior.cyber.frmk.common.model.menu.sidebar.*;
import com.senior.cyber.frmk.common.provider.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

public abstract class MasterPage extends WebPage {

    public static final String MESSAGE_KEY = MasterPage.class.getName() + ".message";

    protected String navbarSearchText;

    protected String sidebarSearchText;

    protected IThemeProvider themeProvider;

    protected INavbarProvider navbarProvider;

    protected IMainSidebarProvider mainSidebarProvider;

    protected IFooterProvider footerProvider;

    protected IContentHeaderProvider contentHeaderProvider;

    protected ModalDialog modalDialog;

    private String overlay_dialog_class = "";
    private String body_element_class;
    private Set<String> body_element_classes;
    private String body_element_style;

    protected String overlayWicketId;

    protected String bodyWicketId;

    protected String pageTitle;

    private WebMarkupContainer message_container;

    public MasterPage() {
    }

    public MasterPage(IModel<?> model) {
        super(model);
    }

    public MasterPage(PageParameters parameters) {
        super(parameters);
    }

    protected abstract void onInitData();

    protected abstract void onInitHtml(MarkupContainer body);

    @Override
    protected void onInitialize() {
        super.onInitialize();
        onInitData();

        Theme theme = null;
        if (this.themeProvider == null) {
            theme = new Theme();
        } else {
            theme = this.themeProvider.fetchTheme();
        }

        ContentHeader contentHeader = null;
        if (this.contentHeaderProvider != null) {
            contentHeader = this.contentHeaderProvider.fetchContentHeader();
        }

        boolean hasContentHeader = false;
        if (contentHeader != null && (contentHeader.getCaption() != null && !"".equals(contentHeader.getCaption())) && (contentHeader.getPath() != null && !contentHeader.getPath().isEmpty())) {
            hasContentHeader = true;
        }

        MainSidebar mainSidebar = null;
        if (this.mainSidebarProvider != null) {
            mainSidebar = this.mainSidebarProvider.fetchMainSidebar();
        }

        Navbar navbar = null;
        if (this.navbarProvider != null) {
            navbar = this.navbarProvider.fetchNavbar();
        }

        boolean hasNavbar = false;
        if (navbar != null && (navbar.isSearchable() || (navbar.getLeft() != null && !navbar.getLeft().isEmpty()) || (navbar.getRight() != null && !navbar.getRight().isEmpty()))) {
            hasNavbar = true;
        }

        boolean hasMainSidebar = false;
        if (mainSidebar != null && (mainSidebar.getBrand() != null || mainSidebar.getUserPanel() != null || (mainSidebar.getSidebarMenu() != null && !mainSidebar.getSidebarMenu().isEmpty()))) {
            hasMainSidebar = true;
        }

        Footer footer = null;
        if (this.footerProvider != null) {
            footer = this.footerProvider.fetchFooter();
        }

        boolean hasFooter = false;
        if (footer != null) {
            hasFooter = true;
        }

        Label pageTitle = new Label("pageTitle", new PropertyModel<>(this, "pageTitle"));
        add(pageTitle);

        WebMarkupContainer body_element = new WebMarkupContainer("body_element");
        body_element.setOutputMarkupId(true);
        this.bodyWicketId = body_element.getMarkupId(true);
        add(body_element);

        WebMarkupContainer overlay_dialog = new WebMarkupContainer("overlay_dialog");
        overlay_dialog.setOutputMarkupId(true);

        this.overlayWicketId = overlay_dialog.getMarkupId(true);
        body_element.add(overlay_dialog);

        this.modalDialog = new ModalDialog("modalDialog");
        body_element.add(this.modalDialog);

        overlay_dialog.add(AttributeModifier.replace("class", new PropertyModel<String>(this, "overlay_dialog_class")));

        this.body_element_classes = new TreeSet<>();
        this.body_element_classes.add("hold-transition");

        if (theme.getAccentColor() != null) {
            this.body_element_classes.add(theme.getAccentColor().getStyle());
        }

        if (!hasMainSidebar && !hasNavbar) {
            this.body_element_classes.add("sidebar-collapse");
        } else {
            if (hasMainSidebar) {
                this.body_element_classes.add("sidebar-mini");
                if (theme.isSidebarFixed()) {
                    this.body_element_classes.add("layout-fixed");
                }
                if (theme.isSidebarCollapsible()) {
                    this.body_element_classes.add("sidebar-collapse");
                }
            } else {
                this.body_element_classes.add("layout-top-nav");
            }
        }

        if (hasNavbar && theme.isNavbarFixed()) {
            this.body_element_classes.add("layout-navbar-fixed");
        }

        if (hasFooter && theme.isFooterFixed()) {
            this.body_element_classes.add("layout-footer-fixed");
        }

        body_element.add(AttributeModifier.replace("class", new PropertyModel<String>(this, "body_element_class")));

        body_element.add(AttributeModifier.replace("style", new PropertyModel<String>(this, "body_element_style")));

        WebMarkupContainer content_header_widget = null;
        if (hasContentHeader) {
            content_header_widget = new Fragment("content_header_widget", "fragment_content_header", this);
            if (contentHeader.getCaption() == null || "".equals(contentHeader.getCaption())) {
                Label caption = new Label("caption", "&nbsp;");
                caption.setEscapeModelStrings(false);
                content_header_widget.add(caption);
            } else {
                Label caption = new Label("caption", contentHeader.getCaption());
                content_header_widget.add(caption);
            }
            WebMarkupContainer breadcrumb_container_widget = null;
            if (contentHeader.getPath() == null || contentHeader.getPath().isEmpty()) {
                breadcrumb_container_widget = new WebMarkupContainer("breadcrumb_container_widget");
                breadcrumb_container_widget.setVisible(false);
            } else {
                breadcrumb_container_widget = new Fragment("breadcrumb_container_widget", "fragment_breadcrumb_container", this);
                RepeatingView breadcrumb = new RepeatingView("breadcrumb");
                breadcrumb_container_widget.add(breadcrumb);
                for (Breadcrumb item : contentHeader.getPath()) {
                    if (item.getPage() == null) {
                        Fragment itemWidget = new Fragment(breadcrumb.newChildId(), "fragment_breadcrumb_text", this);
                        breadcrumb.add(itemWidget);
                        Label text = new Label("text", item.getText());
                        itemWidget.add(text);
                    } else {
                        Fragment itemWidget = new Fragment(breadcrumb.newChildId(), "fragment_breadcrumb_page", this);
                        breadcrumb.add(itemWidget);
                        BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", item.getPage(), item.getParameters());
                        itemWidget.add(link);
                        Label text = new Label("text", item.getText());
                        text.setRenderBodyOnly(true);
                        link.add(text);
                    }
                }
            }
            content_header_widget.add(breadcrumb_container_widget);
        } else {
            content_header_widget = new WebMarkupContainer("content_header_widget");
        }
        body_element.add(content_header_widget);

        this.message_container = new WebMarkupContainer("message_container");
        this.message_container.setOutputMarkupId(true);
        body_element.add(this.message_container);

        Label message_label = new Label("message_label", new PropertyModel<>(this, "message")) {
            @Override
            protected void onAfterRender() {
                super.onAfterRender();
                getSession().removeAttribute(MESSAGE_KEY);
            }
        };
        message_label.setEscapeModelStrings(false);
        this.message_container.add(message_label);

        WebMarkupContainer navbar_widget = null;
        if (hasNavbar) {
            navbar_widget = new Fragment("navbar_widget", "fragment_navbar", this);
        } else {
            if (hasMainSidebar) {
                navbar_widget = new Fragment("navbar_widget", "fragment_navbar_empty", this);
            } else {
                navbar_widget = new WebMarkupContainer("navbar_widget");
                navbar_widget.setVisible(false);
            }
        }
        Set<String> navbar_widgetClasses = new TreeSet<>();
        navbar_widgetClasses.add("main-header");
        navbar_widgetClasses.add("navbar");
        navbar_widgetClasses.add("navbar-expand");
        navbar_widgetClasses.add("navbar-white");
        navbar_widgetClasses.add("navbar-light");
        if (navbar != null) {
            if (theme.isNavbarSmallText()) {
                navbar_widgetClasses.add("text-sm");
            }
            if (theme.getNavbarTheme() != null) {
                navbar_widgetClasses.add(theme.getNavbarTheme().getStyle());
            }
            if (theme.getNavbarColor() != null) {
                navbar_widgetClasses.add(theme.getNavbarColor().getStyle());
            }
        }
        navbar_widget.add(AttributeModifier.replace("class", StringUtils.join(navbar_widgetClasses, " ")));
        body_element.add(navbar_widget);

        Fragment navbar_left_menu_widget = new Fragment("navbar_left_menu_widget", "fragment_navbar_left_menu", this);
        navbar_widget.add(navbar_left_menu_widget);

        if (hasMainSidebar) {
            Fragment hamburger_widget = new Fragment("hamburger_widget", "fragment_hamburger", this);
            navbar_left_menu_widget.add(hamburger_widget);
        } else {
            WebMarkupContainer hamburger_widget = new WebMarkupContainer("hamburger_widget");
            hamburger_widget.setVisible(false);
            navbar_left_menu_widget.add(hamburger_widget);
        }

        if (hasNavbar) {
            List<TopLeftMenu> topLeftMenu = navbar.getLeft();
            RepeatingView top_left_menu_container_widget = new RepeatingView("top_left_menu_container_widget");
            navbar_left_menu_widget.add(top_left_menu_container_widget);
            if (topLeftMenu != null && !topLeftMenu.isEmpty()) {
                for (TopLeftMenu item : topLeftMenu) {
                    if (item instanceof TopLeftMenuItem) {
                        if (((TopLeftMenuItem) item).getPage() != null) {
                            String childId = top_left_menu_container_widget.newChildId();
                            Fragment topLeftMenuItem = new Fragment(childId, "fragment_top_left_menu_item", this);
                            top_left_menu_container_widget.add(topLeftMenuItem);
                            BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", ((TopLeftMenuItem) item).getPage(), ((TopLeftMenuItem) item).getParameters());
                            topLeftMenuItem.add(link);
                            Label text = new Label("text", ((TopLeftMenuItem) item).getText());
                            text.setRenderBodyOnly(true);
                            link.add(text);
                            text.setRenderBodyOnly(true);
                        } else {
                            String childId = top_left_menu_container_widget.newChildId();
                            Fragment topLeftMenuItem = new Fragment(childId, "fragment_top_left_menu_item", this);
                            top_left_menu_container_widget.add(topLeftMenuItem);
                            WebMarkupContainer link = new WebMarkupContainer("link");
                            link.add(AttributeModifier.replace("href", "#"));
                            topLeftMenuItem.add(link);
                            Label text = new Label("text", ((TopLeftMenuItem) item).getText());
                            text.setRenderBodyOnly(true);
                            link.add(text);
                            text.setRenderBodyOnly(true);
                        }
                    } else if (item instanceof TopLeftMenuDropdown) {
                        String childId = top_left_menu_container_widget.newChildId();
                        Fragment topLeftMenuDropdown = new Fragment(childId, "fragment_top_left_menu_dropdown", this);
                        top_left_menu_container_widget.add(topLeftMenuDropdown);
                        WebMarkupContainer link = new WebMarkupContainer("link");
                        link.setOutputMarkupId(true);
                        topLeftMenuDropdown.add(link);
                        Label text = new Label("text", ((TopLeftMenuDropdown) item).getText());
                        text.setRenderBodyOnly(true);
                        link.add(text);
                        if (((TopLeftMenuDropdown) item).getChildren().isEmpty()) {
                            WebMarkupContainer children = new WebMarkupContainer("children");
                            topLeftMenuDropdown.add(children);
                        } else {
                            Fragment children = new Fragment("children", "fragment_top_left_sub_menu", this);
                            topLeftMenuDropdown.add(children);

                            children.add(AttributeModifier.replace("aria-labelledby", link.getMarkupId(true)));

                            RepeatingView sub_menu_container_widget = new RepeatingView("sub_menu_container_widget");
                            children.add(sub_menu_container_widget);
                            for (TopLeftSubMenu topLeftSubMenu : ((TopLeftMenuDropdown) item).getChildren()) {
                                renderTopLeftSubMenu(sub_menu_container_widget, topLeftSubMenu);
                            }
                        }
                    }
                }
            }
            WebMarkupContainer navbar_search_widget = null;
            if (navbar.isSearchable()) {
                navbar_search_widget = new Fragment("navbar_search_widget", "fragment_navbar_search", this);
                Form<Void> search_form = new Form<>("search_form");
                navbar_search_widget.add(search_form);
                TextField<String> search_field = new TextField<>("search_field", new PropertyModel<>(this, "navbarSearchText"));
                search_form.add(search_field);
                Button search_button = new Button("search_button") {
                    @Override
                    public void onSubmit() {
                        onNavbarSearch(navbarSearchText);
                    }
                };
                search_form.add(search_button);
            } else {
                navbar_search_widget = new WebMarkupContainer("navbar_search_widget");
                navbar_search_widget.setVisible(false);
            }
            navbar_widget.add(navbar_search_widget);

            Fragment navbar_right_menu_widget = new Fragment("navbar_right_menu_widget", "fragment_navbar_right_menu", this);
            navbar_widget.add(navbar_right_menu_widget);

            RepeatingView top_right_menu_container_widget = new RepeatingView("top_right_menu_container_widget");
            navbar_right_menu_widget.add(top_right_menu_container_widget);

            List<TopRightMenu> topRightMenu = navbar.getRight();
            if (topRightMenu != null && !topRightMenu.isEmpty()) {
                for (TopRightMenu menu : topRightMenu) {
                    if (menu instanceof TopRightMenuItem) {
                        String childId = top_right_menu_container_widget.newChildId();
                        Fragment topRightMenuItem = new Fragment(childId, "fragment_top_right_menu_item", this);
                        top_right_menu_container_widget.add(topRightMenuItem);
                        String html = ((TopRightMenuItem) menu).getText();
                        if (((TopRightMenuItem) menu).getPage() == null) {
                            WebMarkupContainer link = new WebMarkupContainer("link");
                            link.add(AttributeModifier.replace("href", "#"));
                            topRightMenuItem.add(link);
                            Label text = new Label("text", html + " ");
                            link.add(text);
                        } else {
                            BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", ((TopRightMenuItem) menu).getPage(), ((TopRightMenuItem) menu).getParameters());
                            topRightMenuItem.add(link);
                            Label text = new Label("text", html + " ");
                            link.add(text);
                        }
                    } else if (menu instanceof TopRightMenuDropdown) {
                        String childId = top_right_menu_container_widget.newChildId();
                        Fragment topRightMenuDropdown = new Fragment(childId, "fragment_top_right_menu_dropdown", this);
                        top_right_menu_container_widget.add(topRightMenuDropdown);
                        {
                            String html = ((TopRightMenuDropdown) menu).getText();
                            Label text = new Label("text", html + " ");
                            text.setEscapeModelStrings(false);
                            if (html == null || "".equals(html)) {
                                text.setVisible(false);
                            }
                            topRightMenuDropdown.add(text);
                        }
                        {
                            WebMarkupContainer icon = new WebMarkupContainer("icon");
                            topRightMenuDropdown.add(icon);
                            icon.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList(((TopRightMenuDropdown) menu).getIcon()), " ")));
                        }
                        Label badge = new Label("badge", ((TopRightMenuDropdown) menu).getBadge() != null ? ((TopRightMenuDropdown) menu).getBadge().getText() : "");
                        topRightMenuDropdown.add(badge);
                        if (((TopRightMenuDropdown) menu).getBadge() == null) {
                            badge.setVisible(false);
                        } else {
                            badge.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList("badge", "navbar-badge", ((TopRightMenuDropdown) menu).getBadge().getType()), " ")));
                        }

                        RepeatingView children = new RepeatingView("children");
                        topRightMenuDropdown.add(children);

                        List<TopRightMenu> items = ((TopRightMenuDropdown) menu).getChildren();
                        if (items != null && !items.isEmpty()) {
                            boolean divider = false;
                            if (items.size() > 1) {
                                divider = true;
                            }
                            for (int i = 0; i < items.size(); i++) {
                                TopRightMenu item = items.get(i);
                                if (divider && i >= 1) {
                                    String cid = children.newChildId();
                                    Fragment topRightMenuDivider = new Fragment(cid, "fragment_top_right_menu_divider", this);
                                    children.add(topRightMenuDivider);
                                }
                                if (item instanceof TopRightMenuSubHeader) {
                                    String cid = children.newChildId();
                                    Fragment topRightMenuHeader = new Fragment(cid, "fragment_top_right_menu_header", this);
                                    children.add(topRightMenuHeader);
                                    Label text_ = new Label("text", ((TopRightMenuSubHeader) item).getText());
                                    topRightMenuHeader.add(text_);
                                } else if (item instanceof TopRightMenuSubFooter) {
                                    String cid = children.newChildId();
                                    Fragment topRightMenuFooter = new Fragment(cid, "fragment_top_right_menu_footer", this);
                                    children.add(topRightMenuFooter);
                                    Label text_ = new Label("text", ((TopRightMenuSubFooter) item).getText());
                                    topRightMenuFooter.add(text_);
                                } else if (item instanceof TopRightMenuSubSimpleItem) {
                                    String cid = children.newChildId();
                                    Fragment topRightMenuSimpleItem = new Fragment(cid, "fragment_top_right_menu_simple_item", this);
                                    children.add(topRightMenuSimpleItem);
                                    BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", ((TopRightMenuSubSimpleItem) item).getPage(), ((TopRightMenuSubSimpleItem) item).getParameters());
                                    topRightMenuSimpleItem.add(link);
                                    WebMarkupContainer icon = new WebMarkupContainer("icon");
                                    link.add(icon);
                                    icon.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList("mr-2", ((TopRightMenuSubSimpleItem) item).getIcon()), " ")));
                                    Label text = new Label("text", ((TopRightMenuSubSimpleItem) item).getTitle());
                                    link.add(text);
                                    Label caption = new Label("caption", ((TopRightMenuSubSimpleItem) item).getCaption());
                                    link.add(caption);
                                } else if (item instanceof TopRightMenuSubCardItem) {
                                    String cid = children.newChildId();
                                    Fragment topRightMenuCardItem = new Fragment(cid, "fragment_top_right_menu_card_item", this);
                                    children.add(topRightMenuCardItem);
                                    BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", ((TopRightMenuSubCardItem) item).getPage(), ((TopRightMenuSubCardItem) item).getParameters());
                                    topRightMenuCardItem.add(link);
                                    Image avatar_image = new Image("avatar_image", ((TopRightMenuSubCardItem) item).getAvatar());
                                    link.add(avatar_image);
                                    Label title = new Label("title", ((TopRightMenuSubCardItem) item).getTitle());
                                    link.add(title);
                                    WebMarkupContainer emoji = new WebMarkupContainer("emoji");
                                    link.add(emoji);
                                    if (((TopRightMenuSubCardItem) item).getEmoji() != null) {
                                        WebMarkupContainer emoji_type = new WebMarkupContainer("emoji_type");
                                        emoji.add(emoji_type);
                                        emoji_type.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList("float-right", "text-sm", ((TopRightMenuSubCardItem) item).getEmoji().getType()), " ")));
                                        WebMarkupContainer emoji_icon = new WebMarkupContainer("emoji_icon");
                                        emoji_type.add(emoji_icon);
                                        emoji_icon.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList(((TopRightMenuSubCardItem) item).getEmoji().getIcon()), " ")));
                                    } else {
                                        emoji.setVisible(false);
                                        WebMarkupContainer emoji_type = new WebMarkupContainer("emoji_type");
                                        emoji.add(emoji_type);
                                        WebMarkupContainer emoji_icon = new WebMarkupContainer("emoji_icon");
                                        emoji_type.add(emoji_icon);
                                    }
                                    Label description = new Label("description", ((TopRightMenuSubCardItem) item).getDescription());
                                    link.add(description);
                                    WebMarkupContainer caption = new WebMarkupContainer("caption");
                                    link.add(caption);
                                    if (((TopRightMenuSubCardItem) item).getCaption() != null) {
                                        WebMarkupContainer caption_icon = new WebMarkupContainer("caption_icon");
                                        caption.add(caption_icon);
                                        caption_icon.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList("mr-1", ((TopRightMenuSubCardItem) item).getCaption().getIcon()), " ")));
                                        Label caption_text = new Label("caption_text", ((TopRightMenuSubCardItem) item).getCaption().getText());
                                        caption.add(caption_text);
                                    } else {
                                        caption.setVisible(false);
                                        WebMarkupContainer caption_icon = new WebMarkupContainer("caption_icon");
                                        caption.add(caption_icon);
                                        WebMarkupContainer caption_text = new WebMarkupContainer("caption_text");
                                        caption.add(caption_text);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        WebMarkupContainer main_sidebar_widget = null;
        if (hasMainSidebar) {
            main_sidebar_widget = new Fragment("main_sidebar_widget", "fragment_main_sidebar", this);
            Set<String> main_sidebar_widgetClasses = new TreeSet<>();
            main_sidebar_widgetClasses.add("main-sidebar");
            main_sidebar_widgetClasses.add("elevation-4");
            if (theme.getSidebarColor() == null) {
                main_sidebar_widgetClasses.add("sidebar-dark-primary ");
            } else {
                main_sidebar_widgetClasses.add(theme.getSidebarColor().getStyle());
            }
            main_sidebar_widget.add(AttributeModifier.replace("class", StringUtils.join(main_sidebar_widgetClasses, " ")));

            WebMarkupContainer brand_widget = null;
            if (mainSidebar.getBrand() != null) {
                brand_widget = new Fragment("brand_widget", "fragment_brand", this);

                BookmarkablePageLink<Void> brand_link = new BookmarkablePageLink<>("brand_link", mainSidebar.getBrand().getPage(), mainSidebar.getBrand().getParameters());
                Set<String> brand_linkClasses = new TreeSet<>();
                brand_linkClasses.add("brand-link");
                if (theme.isBrandSmallText()) {
                    brand_linkClasses.add("text-sm");
                }
                if (theme.getBrandColor() != null) {
                    brand_linkClasses.add(theme.getBrandColor().getStyle());
                }
                brand_link.add(AttributeModifier.replace("class", StringUtils.join(brand_linkClasses, " ")));
                brand_widget.add(brand_link);

                Image image = new Image("image", mainSidebar.getBrand().getImage());
                brand_link.add(image);

                Label text = new Label("text", mainSidebar.getBrand().getText());
                brand_link.add(text);
            } else {
                brand_widget = new WebMarkupContainer("brand_widget");
                brand_widget.setVisible(false);
            }
            main_sidebar_widget.add(brand_widget);

            WebMarkupContainer sidebar_widget = null;
            if (mainSidebar.getUserPanel() == null && (mainSidebar.getSidebarMenu() == null || mainSidebar.getSidebarMenu().isEmpty()) && !mainSidebar.isSearchable()) {
                sidebar_widget = new WebMarkupContainer("sidebar_widget");
                sidebar_widget.setVisible(false);
            } else {
                sidebar_widget = new Fragment("sidebar_widget", "fragment_sidebar", this);
                WebMarkupContainer user_panel_widget = null;
                if (mainSidebar.getUserPanel() != null) {
                    user_panel_widget = new Fragment("user_panel_widget", "fragment_user_panel", this);

                    Image image = new Image("image", mainSidebar.getUserPanel().getImage());
                    user_panel_widget.add(image);

                    BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", mainSidebar.getUserPanel().getPage(), mainSidebar.getUserPanel().getParameters());
                    user_panel_widget.add(link);

                    Label text = new Label("text", mainSidebar.getUserPanel().getText());
                    link.add(text);
                } else {
                    user_panel_widget = new WebMarkupContainer("user_panel_widget");
                    user_panel_widget.setVisible(false);
                }
                sidebar_widget.add(user_panel_widget);

                WebMarkupContainer sidebar_search_widget = null;
                if (mainSidebar.isSearchable()) {
                    sidebar_search_widget = new Fragment("sidebar_search_widget", "fragment_sidebar_search", this);

                    Form<Void> search_form = new Form<>("search_form");
                    sidebar_search_widget.add(search_form);
                    TextField<String> search_field = new TextField<>("search_field", new PropertyModel<>(this, "sidebarSearchText"));
                    search_form.add(search_field);
                    Button search_button = new Button("search_button") {
                        @Override
                        public void onSubmit() {
                            onSidebarSearch(sidebarSearchText);
                        }
                    };
                    search_form.add(search_button);
                } else {
                    sidebar_search_widget = new WebMarkupContainer("sidebar_search_widget");
                    sidebar_search_widget.setVisible(false);
                }
                sidebar_widget.add(sidebar_search_widget);

                Fragment sidebar_menu_container_widget = new Fragment("sidebar_menu_container_widget", "fragment_sidebar_menu_container", this);
                sidebar_widget.add(sidebar_menu_container_widget);

                WebMarkupContainer sidebar_menu_controller = new WebMarkupContainer("sidebar_menu_controller");
                sidebar_menu_container_widget.add(sidebar_menu_controller);

                Set<String> sidebarMenuClasses = new TreeSet<>();
                sidebarMenuClasses.add("nav");
                sidebarMenuClasses.add("nav-pills");
                sidebarMenuClasses.add("nav-sidebar");
                sidebarMenuClasses.add("flex-column");
                if (theme.isSidebarCompact()) {
                    sidebarMenuClasses.add("nav-compact");
                }
                if (theme.isSidebarLegacy()) {
                    sidebarMenuClasses.add("nav-legacy");
                }
                if (theme.getSidebarHierarchyType() == HierarchyType.Flat) {
                    sidebarMenuClasses.add("nav-flat");
                } else if (theme.getSidebarHierarchyType() == HierarchyType.Indentation) {
                    sidebarMenuClasses.add("nav-child-indent");
                }
                if (theme.isSidebarSmallText()) {
                    sidebarMenuClasses.add("text-sm");
                }
                sidebar_menu_controller.add(AttributeModifier.replace("class", StringUtils.join(sidebarMenuClasses, " ")));

                List<SidebarMenu> sidebarMenu = mainSidebar.getSidebarMenu();
                RepeatingView children = new RepeatingView("children");
                sidebar_menu_controller.add(children);
                if (sidebarMenu != null && !sidebarMenu.isEmpty()) {
                    for (SidebarMenu item : sidebarMenu) {
                        renderSidebarMenu(children, item);
                    }
                }
            }
            main_sidebar_widget.add(sidebar_widget);
        } else {
            main_sidebar_widget = new WebMarkupContainer("main_sidebar_widget");
            main_sidebar_widget.setVisible(false);
        }
        body_element.add(main_sidebar_widget);

        WebMarkupContainer controlSidebarWidget = new WebMarkupContainer("controlSidebarWidget");
        body_element.add(controlSidebarWidget);

        WebMarkupContainer footer_widget = null;
        if (hasFooter) {
            footer_widget = new Fragment("footer_widget", "fragment_footer", this);
            Set<String> footer_widgetClasses = new TreeSet<>();
            footer_widgetClasses.add("main-footer");
            if (theme.isFooterSmallText()) {
                footer_widgetClasses.add("text-sm");
            }
            footer_widget.add(AttributeModifier.replace("class", StringUtils.join(footer_widgetClasses, " ")));
            Label version = new Label("version", footer.getVersion());
            footer_widget.add(version);
            Label year = new Label("year", LocalDate.now().getYear());
            footer_widget.add(year);
            BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", footer.getPage(), footer.getParameters());
            footer_widget.add(link);
            Label text = new Label("text", footer.getAuthor());
            text.setRenderBodyOnly(true);
            link.add(text);
        } else {
            footer_widget = new WebMarkupContainer("footer_widget");
            footer_widget.setVisible(false);
        }
        body_element.add(footer_widget);

        onInitHtml(body_element);
    }

    protected void onNavbarSearch(String searchText) {
    }

    protected void onSidebarSearch(String searchText) {
    }

    protected void renderSidebarMenu(RepeatingView containerWidget, SidebarMenu item) {
        if (item instanceof SidebarMenuItem) {
            String childId = containerWidget.newChildId();
            Fragment itemWidget = new Fragment(childId, "fragment_sidebar_menu_item", this);
            containerWidget.add(itemWidget);
            BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", ((SidebarMenuItem) item).getPage(), ((SidebarMenuItem) item).getParameters());
            itemWidget.add(link);
            WebMarkupContainer icon = new WebMarkupContainer("icon");
            link.add(icon);
            icon.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList("nav-icon", ((SidebarMenuItem) item).getIcon()), " ")));
            Label text = new Label("text", ((SidebarMenuItem) item).getText());
            text.setRenderBodyOnly(true);
            link.add(text);
            if (((SidebarMenuItem) item).getBadge() == null) {
                WebMarkupContainer badge = new WebMarkupContainer("badge");
                link.add(badge);
                badge.setVisible(false);
            } else {
                Label badge = new Label("badge", ((SidebarMenuItem) item).getBadge().getText());
                link.add(badge);
                badge.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList("right", "badge", ((SidebarMenuItem) item).getBadge().getType()), " ")));
            }
            if (item.getPages().contains(getClass().getName())) {
                link.add(AttributeModifier.replace("class", "nav-link active"));
            } else {
                link.add(AttributeModifier.replace("class", "nav-link"));
            }
        } else if (item instanceof SidebarMenuDropdown) {
            String childId = containerWidget.newChildId();
            Fragment itemWidget = new Fragment(childId, "fragment_sidebar_menu_dropdown", this);
            containerWidget.add(itemWidget);
            WebMarkupContainer menu_tree = new WebMarkupContainer("menu_tree");
            if (item.getPages().contains(this.getClass().getName())) {
                menu_tree.add(AttributeModifier.replace("class", "nav-item has-treeview menu-open"));
            } else {
                menu_tree.add(AttributeModifier.replace("class", "nav-item has-treeview"));
            }
            itemWidget.add(menu_tree);
            WebMarkupContainer menu_link = new WebMarkupContainer("menu_link");
            menu_tree.add(menu_link);
            if (item.getPages().contains(this.getClass().getName())) {
                menu_link.add(AttributeModifier.replace("class", "nav-link active"));
            } else {
                menu_link.add(AttributeModifier.replace("class", "nav-link"));
            }
            WebMarkupContainer icon = new WebMarkupContainer("icon");
            menu_link.add(icon);
            icon.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList("nav-icon", ((SidebarMenuDropdown) item).getIcon()), " ")));
            Label text = new Label("text", ((SidebarMenuDropdown) item).getText());
            text.setRenderBodyOnly(true);
            menu_link.add(text);
            if (((SidebarMenuDropdown) item).getBadge() == null) {
                WebMarkupContainer badge = new WebMarkupContainer("badge");
                badge.setVisible(false);
                menu_link.add(badge);
            } else {
                Label badge = new Label("badge", ((SidebarMenuDropdown) item).getBadge().getText());
                menu_link.add(badge);
                badge.add(AttributeModifier.replace("class", StringUtils.join(Arrays.asList("right", "badge", ((SidebarMenuDropdown) item).getBadge().getType()), " ")));
            }
            RepeatingView children = new RepeatingView("children");
            menu_tree.add(children);
            for (SidebarMenu child : ((SidebarMenuDropdown) item).getChildren()) {
                renderSidebarMenu(children, child);
            }
        } else if (item instanceof SidebarMenuCaption) {
            String childId = containerWidget.newChildId();
            Fragment itemWidget = new Fragment(childId, "fragment_sidebar_menu_caption", this);
            containerWidget.add(itemWidget);
            Label text = new Label("text", ((SidebarMenuCaption) item).getText());
            itemWidget.add(text);
        }
    }

    protected void renderTopLeftSubMenu(RepeatingView containerWidget, TopLeftSubMenu subMenu) {
        if (subMenu instanceof TopLeftSubMenuItem) {
            String childId = containerWidget.newChildId();
            Fragment topLeftMenuItem = new Fragment(childId, "fragment_top_left_sub_menu_item", this);
            containerWidget.add(topLeftMenuItem);
            BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", ((TopLeftSubMenuItem) subMenu).getPage(), ((TopLeftSubMenuItem) subMenu).getParameters());
            topLeftMenuItem.add(link);
            Label text = new Label("text", ((TopLeftSubMenuItem) subMenu).getText());
            text.setRenderBodyOnly(true);
            link.add(text);
            text.setRenderBodyOnly(true);
        } else if (subMenu instanceof TopLeftSubMenuDivider) {
            String childId = containerWidget.newChildId();
            Fragment topLeftMenuItem = new Fragment(childId, "fragment_top_left_sub_menu_divider", this);
            containerWidget.add(topLeftMenuItem);
        } else if (subMenu instanceof TopLeftSubMenuDropdown) {
            String childId = containerWidget.newChildId();
            if (((TopLeftSubMenuDropdown) subMenu).getChildren().isEmpty()) {
                WebMarkupContainer topLeftMenuWidget = new WebMarkupContainer(childId);
                containerWidget.add(topLeftMenuWidget);
            } else {
                Fragment topLeftMenuWidget = new Fragment(childId, "fragment_top_left_sub_menu_dropdown", this);
                containerWidget.add(topLeftMenuWidget);

                WebMarkupContainer link = new WebMarkupContainer("link");
                link.setOutputMarkupId(true);
                topLeftMenuWidget.add(link);
                Label text = new Label("text", ((TopLeftSubMenuDropdown) subMenu).getText());
                text.setRenderBodyOnly(true);
                link.add(text);

                WebMarkupContainer children_container = new WebMarkupContainer("children_container");
                children_container.add(AttributeModifier.replace("aria-labelledby", link.getMarkupId(true)));
                topLeftMenuWidget.add(children_container);

                RepeatingView children = new RepeatingView("children");
                children_container.add(children);
                for (TopLeftSubMenu topLeftSubMenu : ((TopLeftSubMenuDropdown) subMenu).getChildren()) {
                    renderTopLeftSubMenu(children, topLeftSubMenu);
                }
            }
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        File adminLte = ((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte();
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/@fontsource/source-sans-3@5.0.12/index.css"));
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/overlayscrollbars@2.10.1/styles/overlayscrollbars.min.css"));
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"));
        response.render(CssHeaderItem.forReference(new PackageResourceReference(Pkg.class, "v4.0.0-beta3/css/adminlte.css")));
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/apexcharts@3.37.1/dist/apexcharts.css"));
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/jsvectormap@1.5.3/dist/css/jsvectormap.min.css"));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/overlayscrollbars@2.10.1/browser/overlayscrollbars.browser.es6.min.js"));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"));
        response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(Pkg.class, "v4.0.0-beta3/js/adminlte.js")));
        StringBuilder js = new StringBuilder();
        js.append("<script>").append("\n");
        js.append("    const SELECTOR_SIDEBAR_WRAPPER = \".sidebar-wrapper\";").append("\n");
        js.append("    const Default = {").append("\n");
        js.append("        scrollbarTheme: \"os-theme-light\",").append("\n");
        js.append("        scrollbarAutoHide: \"leave\",").append("\n");
        js.append("        scrollbarClickScroll: true,").append("\n");
        js.append("    };").append("\n");
        js.append("    document.addEventListener(\"DOMContentLoaded\", function () {").append("\n");
        js.append("        const sidebarWrapper = document.querySelector(SELECTOR_SIDEBAR_WRAPPER);").append("\n");
        js.append("        if (").append("\n");
        js.append("            sidebarWrapper &&").append("\n");
        js.append("            typeof OverlayScrollbarsGlobal?.OverlayScrollbars !== \"undefined\"").append("\n");
        js.append("        ) {").append("\n");
        js.append("            OverlayScrollbarsGlobal.OverlayScrollbars(sidebarWrapper, {").append("\n");
        js.append("                scrollbars: {").append("\n");
        js.append("                    theme: Default.scrollbarTheme,").append("\n");
        js.append("                    autoHide: Default.scrollbarAutoHide,").append("\n");
        js.append("                    clickScroll: Default.scrollbarClickScroll,").append("\n");
        js.append("                },").append("\n");
        js.append("            });").append("\n");
        js.append("        }").append("\n");
        js.append("    });").append("\n");
        js.append("</script>").append("\n");
        response.render(JavaScriptHeaderItem.forScript(js.toString(), UUID.randomUUID().toString()));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/sortablejs@1.15.0/Sortable.min.js"));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/apexcharts@3.37.1/dist/apexcharts.min.js"));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/jsvectormap@1.5.3/dist/js/jsvectormap.min.js"));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/jsvectormap@1.5.3/dist/maps/world.js"));
    }

    public String getNavbarSearchText() {
        return navbarSearchText;
    }

    public void setNavbarSearchText(String navbarSearchText) {
        this.navbarSearchText = navbarSearchText;
    }

    public final String getMessage() {
        String[] messages = (String[]) getSession().getAttribute(MESSAGE_KEY);
        if (messages != null && messages.length != 0) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("<ul style=\"padding:10px 0px 10px 30px\" class=\"alert alert-danger\">");
            for (String message : messages) {
                if (message != null && !"".equals(message)) {
                    buffer.append("<li>").append(StringEscapeUtils.escapeHtml4(message)).append("</li>");
                }
            }
            buffer.append("</ul>");
            return buffer.toString();
        } else {
            return "";
        }
    }

    public final void setMessage(String... messages) {
        getSession().setAttribute(MESSAGE_KEY, messages);
    }

    public final WebMarkupContainer getMessageContainer() {
        return this.message_container;
    }

    @Override
    protected void onBeforeRender() {
        if (this.modalDialog.isOpen()) {
            this.overlay_dialog_class = "modal-backdrop fade show";
            this.body_element_classes.add("modal-open");
            this.body_element_style = "height: auto; padding-right: 17px;";
        } else {
            this.overlay_dialog_class = "";
            this.body_element_classes.remove("modal-open");
            this.body_element_style = "height: auto;";
        }
        this.body_element_class = StringUtils.join(this.body_element_classes, " ");
        super.onBeforeRender();
    }

    public ModalDialog getModalDialog() {
        return modalDialog;
    }

}
