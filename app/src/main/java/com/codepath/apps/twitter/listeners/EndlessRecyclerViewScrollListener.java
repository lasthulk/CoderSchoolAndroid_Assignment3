package com.codepath.apps.twitter.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by toan on 3/27/2016.
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;
    private LinearLayoutManager linearLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.linearLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItem = this.linearLayoutManager.findLastVisibleItemPosition();
        int totalItemsCount = this.linearLayoutManager.getItemCount();
        if (totalItemsCount < this.previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemsCount;
            if (totalItemsCount == 0) {
                this.loading = true;
            }
        }
        if (loading && (totalItemsCount > this.previousTotalItemCount)) {
            this.loading = false;
            this.previousTotalItemCount = totalItemsCount;
        }
        if (!loading && totalItemsCount <= (lastVisibleItem + visibleThreshold)) {
            this.currentPage++;
            onLoadMore(currentPage, totalItemsCount);
            this.loading = true;
        }
    }

    public abstract void onLoadMore(int page, int totalItemsCount);
}
