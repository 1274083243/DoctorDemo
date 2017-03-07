package com.ike.commonutils.recycleViewUtils;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
作者：ike
时间：2017/3/7 16:19
功能描述：为recyclevie添加头尾布局的包装类
**/
public class HeadAndFootWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String Tag="HeaderAndFooterWrapper";
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;

    public HeadAndFootWrapper(RecyclerView.Adapter adapter)
    {
        mInnerAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (mHeaderViews.get(viewType) != null)
        {
            ViewHolder holder=ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));


            return holder;

        } else if (mFootViews.get(viewType) != null)
        {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // 若是头布局或是尾布局，不需要绑定数据
            if (isHeaderViewPos(position)){
                return;
            }
            if (isFooterViewPos(position)){
                return;
            }

            mInnerAdapter.onBindViewHolder(holder,position-getHeadersCount());

    }

    @Override
    public int getItemViewType(int position)
    {
        if (isHeaderViewPos(position))
        {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position))
        {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }
    @Override
    public int getItemCount() {
        return getHeadersCount()+getRealItemCount()+getFootersCount();
    }



    private boolean isHeaderViewPos(int position)
    {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position)
    {
        return position >= getHeadersCount() + getRealItemCount();
    }
    public int getHeadersCount()
    {
        return mHeaderViews.size();
    }

    public int getFootersCount()
    {
        return mFootViews.size();
    }


    /**
     * 获取普通数据条目的个数
     * @return
     */
    public int getRealItemCount(){
        return mInnerAdapter.getItemCount();
    }

    /**
     * 添加头布局
     * @param view
     */
    public  void addHeadView(View view){
        mHeaderViews.put(mHeaderViews.size()+BASE_ITEM_TYPE_HEADER,view);

    }
    /**
     * 添加尾不局
     * @param view
     */
   public void addFooterView(View view){
       mFootViews.put(mFootViews.size()+BASE_ITEM_TYPE_FOOTER,view);
   }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isFooterViewPos(position) || isHeaderViewPos(position))
        {
            WrapperUtils.setFullSpan(holder);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null)
                {
                    return layoutManager.getSpanCount();
                } else if (mFootViews.get(viewType) != null)
                {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }
}
