package com.jdemaagd.meusfilmes.decorators;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jdemaagd.meusfilmes.R;

public class GridItemDecorator extends RecyclerView.ItemDecoration {

    private final Context mContext;

    public GridItemDecorator(Context context) {
        this.mContext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager.LayoutParams layoutParams
                = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        int position = layoutParams.getViewLayoutPosition();
        if (position == RecyclerView.NO_POSITION) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        int itemDivider = mContext.getResources().getDimensionPixelSize(R.dimen.grid_item_divider);

        outRect.top = itemDivider;
        outRect.bottom = itemDivider;
        outRect.left = itemDivider;
        outRect.right = itemDivider;
    }
}
