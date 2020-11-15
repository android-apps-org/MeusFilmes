package com.jdemaagd.meusfilmes.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerViewPagination extends RecyclerView.OnScrollListener {

    private static final String LOG_TAG = RecyclerViewPagination.class.getSimpleName();

    private GridLayoutManager gridLayoutManager;

    public RecyclerViewPagination(GridLayoutManager layoutManager) {
        this.gridLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = gridLayoutManager.getChildCount();
        int totalItemCount = gridLayoutManager.getItemCount();
        int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                Log.i(LOG_TAG, "Loading more items");
                loadMoreItems();
            }
        }
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    protected abstract void loadMoreItems();

    public abstract int totalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}
