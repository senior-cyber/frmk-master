package com.senior.cyber.frmk.common.wicket.markup.repeater;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Generics;

import java.io.Serial;
import java.util.Iterator;

/**
 * @see org.apache.wicket.markup.repeater.RefreshingView
 */
public abstract class RefreshingView<RowType> extends RepeatingView {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The item reuse strategy that will be used to recycle items when the page is changed or the
     * view is redrawn.
     *
     * @see IItemReuseStrategy
     */
    private IItemReuseStrategy<RowType> itemReuseStrategy;

    /**
     * Constructor
     *
     * @param id component id
     */
    public RefreshingView(String id) {
        super(id);
    }

    /**
     * Constructor
     *
     * @param id    component id
     * @param model model
     */
    public RefreshingView(String id, IModel<?> model) {
        super(id, model);
    }

    /**
     * Refresh the items in the view. Delegates the creation of items to the selected item reuse
     * strategy
     */
    @Override
    protected final void onPopulate() {
        Iterator<IModel<RowType>> models = getItemModels();
        Iterator<Item<RowType>> items = getItemReuseStrategy().getItems(newItemFactory(), models, getItems());
        removeAll();
        addItems(items);
    }

    /**
     * Create a new IItemFactory based upon the RefreshingView
     *
     * @return An Item factory that delegates to the RefreshingView
     */
    protected IItemFactory<RowType> newItemFactory() {
        return new DefaultItemFactory<>(this);
    }


    /**
     * Returns an iterator over models for items that will be added to this view
     *
     * @return an iterator over models for items that will be added to this view
     */
    protected abstract Iterator<IModel<RowType>> getItemModels();

    /**
     * Populate the given Item container.
     * <p>
     * <b>be careful</b> to add any components to the item and not the view itself. So, don't do:
     *
     * <pre>
     * add(new Label(&quot;foo&quot;, &quot;bar&quot;));
     * </pre>
     * <p>
     * but:
     *
     * <pre>
     * item.add(new Label(&quot;foo&quot;, &quot;bar&quot;));
     * </pre>
     *
     * </p>
     *
     * @param item The item to populate
     */
    protected abstract void populateItem(final Item<RowType> item);

    /**
     * Factory method for Item container. Item containers are simple MarkupContainer used to
     * aggregate the user added components for a row inside the view.
     *
     * @param id    component id for the new data item
     * @param index the index of the new data item
     * @param model the model for the new data item
     * @return DataItem created DataItem
     * @see Item
     */
    protected Item<RowType> newItem(final String id, int index, final IModel<RowType> model) {
        return new Item<>(id, index, model);
    }

    /**
     * @return iterator over item instances that exist as children of this view
     */
    public Iterator<Item<RowType>> getItems() {
        return Generics.iterator(iterator());
    }

    /**
     * Add items to the view. Prior to this all items were removed so every request this function
     * starts from a clean slate.
     *
     * @param items item instances to be added to this view
     */
    protected void addItems(Iterator<Item<RowType>> items) {
        int index = 0;
        while (items.hasNext()) {
            Item<RowType> item = items.next();
            item.setIndex(index);
            add(item);
            ++index;
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // ITEM GENERATION
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @return currently set item reuse strategy. Defaults to <code>DefaultItemReuseStrategy</code>
     * if none was set.
     * @see DefaultItemReuseStrategy
     */
    public IItemReuseStrategy<RowType> getItemReuseStrategy() {
        if (itemReuseStrategy == null) {
            return new DefaultItemReuseStrategy<>();
        }
        return itemReuseStrategy;
    }

    /**
     * Sets the item reuse strategy. This strategy controls the creation of {@link Item}s.
     *
     * @param strategy item reuse strategy
     * @return this for chaining
     * @see IItemReuseStrategy
     */
    public RefreshingView<RowType> setItemReuseStrategy(IItemReuseStrategy<RowType> strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException();
        }

        if (!strategy.equals(itemReuseStrategy)) {
            if (isVersioned()) {
                addStateChange();
            }
            itemReuseStrategy = strategy;
        }
        return this;
    }


}
