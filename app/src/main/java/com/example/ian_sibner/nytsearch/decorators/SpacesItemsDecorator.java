package com.example.ian_sibner.nytsearch.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ian_sibner on 3/30/17.
 */

/*

Decorator which adds spacing around the tiles in a Grid layout RecyclerView. Apply to a RecyclerView with:

    SpacesItemsDecorator decoration = new SpacesItemsDecorator(16);
    mRecyclerView.addItemDecoration(decoration);

Feel free to add any value you wish for SpacesItemsDecorator. That value determines the amount of spacing.

Source: http://blog.grafixartist.com/pinterest-masonry-layout-staggered-grid/

*/

public class SpacesItemsDecorator extends RecyclerView.ItemDecoration {
    private final int mSpace;
    public SpacesItemsDecorator(int space) {
        this.mSpace = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = mSpace;
    }
}
