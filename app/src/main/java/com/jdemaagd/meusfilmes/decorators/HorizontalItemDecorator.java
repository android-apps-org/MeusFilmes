package com.jdemaagd.meusfilmes.decorators;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.jdemaagd.meusfilmes.R;

public class HorizontalItemDecorator extends RecyclerView.ItemDecoration {

    private final Context mContext;

    public HorizontalItemDecorator(Context context) {
        this.mContext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        if (position == RecyclerView.NO_POSITION) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        int itemDivider = mContext.getResources().getDimensionPixelSize(R.dimen.video_item_divider);
        int itemDividerSmall = mContext.getResources().getDimensionPixelSize(R.dimen.video_item_divider_small);

        outRect.top = itemDividerSmall;
        outRect.bottom = itemDividerSmall;

        if (position == 0) {
            outRect.left = itemDivider;
            outRect.right = itemDividerSmall;
        } else if (position == state.getItemCount() - 1) {
            outRect.left = itemDividerSmall;
            outRect.right = itemDivider;
        } else {
            outRect.left = itemDividerSmall;
            outRect.right = itemDividerSmall;
        }
    }
}
