package com.raksandroid.endlessrecyclerview.utils

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

 class EndlessRecyclerViewScrollListener:RecyclerView.OnScrollListener {
     private var visibleItemCount: Int=0
     private var firstVisibleItemPosition: Int=0

     // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 4
    private var mLoadMoreListener:OnLoadMoreListener?=null
    private var loaded = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int? = 0
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    fun setLoaded(){
        loaded = false
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mLoadMoreListener = mOnLoadMoreListener
    }

    constructor(layoutManager: LinearLayoutManager){
        this.mLayoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager){
        this.mLayoutManager = layoutManager
        visibleThreshold = layoutManager.spanCount
    }


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <=0) return
        if (mLayoutManager is GridLayoutManager){
            lastVisibleItem = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        }else if (mLayoutManager is LinearLayoutManager){
            firstVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            visibleItemCount = (mLayoutManager as LinearLayoutManager).childCount
            Log.d("@@"," lastvisibleItem $lastVisibleItem")
        }
        totalItemCount = mLayoutManager?.itemCount
        if (totalItemCount!=null){
            Log.d("@@","totalItem $totalItemCount")
            if (!loaded && (visibleItemCount + firstVisibleItemPosition) == totalItemCount!!) {
                Log.d("@@@2","end reached")
                mLoadMoreListener?.onLoadMore()
                loaded = true
            }
        }


    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }


}