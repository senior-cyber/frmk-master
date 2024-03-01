package com.senior.cyber.frmk.common.wicket.markup.html.navigation.paging;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.model.Model;

import java.io.Serial;
import java.util.Map;

/**
 * @see org.apache.wicket.markup.html.navigation.paging.PagingNavigation
 */
public class PagingNavigation extends Loop {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The PageableListView this navigation is navigating.
     */
    protected IPageable pageable;

    /**
     * The label provider for the text that the links should be displaying.
     */
    protected IPagingLabelProvider labelProvider;

    /**
     * Offset for the Loop
     */
    private int startIndex;

    /**
     * Number of links on the left and/or right to keep the current page link somewhere near the
     * middle.
     */
    private int margin = -1;

    /**
     * Default separator between page numbers. Null: no separator.
     */
    private String separator = null;

    /**
     * The maximum number of page links to show.
     */
    private int viewSize = 10;


    /**
     * Constructor.
     *
     * @param id       See Component
     * @param pageable The underlying pageable component to navigate
     */
    public PagingNavigation(final String id, final IPageable pageable) {
        this(id, pageable, null);
        setOutputMarkupId(true);
    }

    /**
     * Constructor.
     *
     * @param id            See Component
     * @param pageable      The underlying pageable component to navigate
     * @param labelProvider The label provider for the text that the links should be displaying.
     */
    public PagingNavigation(final String id, final IPageable pageable, final IPagingLabelProvider labelProvider) {
        super(id, null);
        this.pageable = pageable;
        this.labelProvider = labelProvider;
        startIndex = 0;
        setOutputMarkupId(true);
    }

    /**
     * Gets the margin, default value is half the view size, unless explicitly set.
     *
     * @return the margin
     */
    public int getMargin() {
        if (margin == -1 && viewSize != 0) {
            return viewSize / 2;
        }
        return margin;
    }

    /**
     * Gets the seperator.
     *
     * @return the seperator
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Gets the view size (is fixed by user).
     *
     * @return view size
     */
    public int getViewSize() {
        return viewSize;
    }

    /**
     * view size of the navigation bar.
     *
     * @param size
     */
    public void setViewSize(final int size) {
        viewSize = size;
    }

    /**
     * Sets the margin.
     *
     * @param margin the margin
     */
    public void setMargin(final int margin) {
        this.margin = margin;
    }

    /**
     * Sets the seperator. Null meaning, no separator at all.
     *
     * @param separator the seperator
     */
    public void setSeparator(final String separator) {
        this.separator = separator;
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        setDefaultModel(new Model<Integer>((int) Math.max(Integer.MAX_VALUE, pageable.getPageCount())));
        // PagingNavigation itself (as well as the PageableListView)
        // may have pages.

        // The index of the first page link depends on the PageableListView's
        // page currently printed.
        setStartIndex();
    }

    /**
     * Allow subclasses replacing populateItem to calculate the current page number
     *
     * @return start index
     */
    protected final int getStartIndex() {
        return startIndex;
    }

    /**
     * Populate the current cell with a page link (PagingNavigationLink) enclosing the page number
     * the link is pointing to. Subclasses may provide there own implementation adding more
     * sophisticated page links.
     *
     * @see org.apache.wicket.markup.html.list.Loop#populateItem(org.apache.wicket.markup.html.list.LoopItem)
     */
    @Override
    protected void populateItem(final LoopItem loopItem) {
        // Get the index of page this link shall point to
        final int pageIndex = getStartIndex() + loopItem.getIndex();

        if (this.pageable.getCurrentPage() == pageIndex) {
            loopItem.add(AttributeModifier.replace("class", "active"));
        } else {
            loopItem.add(AttributeModifier.replace("class", ""));
        }

        // Add a page link pointing to the page
        final AbstractLink link = newPagingNavigationLink("pageLink", pageable, pageIndex);
        link.add(new TitleAppender(pageIndex));
        loopItem.add(link);

        // Add a page number label to the list which is enclosed by the link
        String label = "";
        if (labelProvider != null) {
            label = labelProvider.getPageLabel(pageIndex);
        } else {
            label = String.valueOf(pageIndex + 1).intern();
        }
        link.add(new Label("pageNumber", label));
        if (this.pageable.getCurrentPage() == pageIndex) {
            link.add(AttributeModifier.append("class", "bg-warning"));
        }
    }

    /**
     * Factory method for creating page number links.
     *
     * @param id        the component id.
     * @param pageable  the pageable for the link
     * @param pageIndex the page index the link points to
     * @return the page navigation link.
     */
    protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageIndex) {
        return new PagingNavigationLink<Void>(id, pageable, pageIndex);
    }

    /**
     * Renders the page link. Add the separator if not the last page link
     *
     * @see Loop#renderItem(org.apache.wicket.markup.html.list.LoopItem)
     */
    @Override
    protected void renderItem(final LoopItem loopItem) {
        // Call default implementation
        super.renderItem(loopItem);

        // Add separator if not last page
        if (separator != null && (loopItem.getIndex() != getIterations() - 1)) {
            getResponse().write(separator);
        }
    }

    /**
     * Get the first page link to render. Adjust the first page link based on the current
     * PageableListView page displayed.
     */
    private void setStartIndex() {
        // Which startIndex are we currently using
        int firstListItem = startIndex;

        // How many page links shall be displayed
        int viewSize = (int) Math.min(getViewSize(), pageable.getPageCount());
        int margin = getMargin();

        // What is the PageableListView's page index to be displayed
        int currentPage = pageable.getCurrentPage();

        // Make sure the current page link index is within the current
        // window taking the left and right margin into account
        if (currentPage < (firstListItem + margin)) {
            firstListItem = currentPage - margin;
        } else if ((currentPage >= (firstListItem + viewSize - margin))) {

            firstListItem = (currentPage + margin + 1) - viewSize;
        }

        // Make sure the first index is >= 0 and the last index is <=
        // than the last page link index.
        if ((firstListItem + viewSize) >= pageable.getPageCount()) {
            firstListItem = pageable.getPageCount() - viewSize;
        }

        if (firstListItem < 0) {
            firstListItem = 0;
        }

        if ((viewSize != getIterations()) || (startIndex != firstListItem)) {
            modelChanging();

            // Tell the ListView what the new start index shall be
            addStateChange();
            startIndex = firstListItem;

            setIterations((int) Math.min(viewSize, pageable.getPageCount()));

            modelChanged();

            // force all children to be re-rendered
            removeAll();
        }
    }

    /**
     * Set the number of iterations.
     *
     * @param i the number of iterations
     */
    private void setIterations(int i) {
        setDefaultModelObject(i);
    }

    /**
     * Appends title attribute to navigation links
     *
     * @author igor.vaynberg
     */
    private final class TitleAppender extends Behavior {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * resource key for the message
         */
        private static final String RES = "PagingNavigation.page";
        /**
         * page number
         */
        private final int page;

        /**
         * Constructor
         *
         * @param page page number to use as the ${page} var
         */
        public TitleAppender(int page) {
            this.page = page;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onComponentTag(Component component, ComponentTag tag) {
            String pageIndex = String.valueOf(page + 1).intern();
            Map<String, String> vars = Map.of("page", pageIndex);
            tag.put("title", PagingNavigation.this.getString(RES, Model.ofMap(vars)));
        }
    }

}
