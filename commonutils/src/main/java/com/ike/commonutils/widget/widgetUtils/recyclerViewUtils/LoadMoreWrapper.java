package com.ike.commonutils.widget.widgetUtils.recyclerViewUtils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ike.commonutils.R;

/**
作者：ike
时间：2017/3/7 9:21
功能描述：加载更多的recycleview
**/
public class LoadMoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int FOOT_ITEM= Integer.MAX_VALUE-2;
    private RecyclerView.Adapter mInnerAdapter;
    private View footView;//加载更多的布局
    private ProgressBar loading_pb;
    private TextView loading_text;
    public boolean isLoading;
    public LoadMoreWrapper(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType==FOOT_ITEM) {

            if (footView!=null){
                ViewHolder viewHolder = ViewHolder.createViewHolder(parent.getContext(), footView);
                return viewHolder;
            }
        }
        return mInnerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMoreView(position)){
            reSetLoading();
            if (listener!=null){

                if (!isLoading){
                    listener.loadMore();
                    isLoading=true;
                }
            }
        }else {
            mInnerAdapter.onBindViewHolder(holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount()+(hasLoadMoreView()?1:0);
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMoreView(position)){
            return FOOT_ITEM;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    /**
     * 判断是否有加载更多的布局
     * @return
     */
    public boolean hasLoadMoreView(){
        return footView!=null;
    }

    /**
     * 判断是否该加载更多的布局显示出来
     * @param position
     * @return
     */
    public boolean isShowLoadMoreView(int position){
        return hasLoadMoreView()&&(position>=mInnerAdapter.getItemCount());
    }
    private OnLoadMoreListener listener;
    public interface OnLoadMoreListener{
        public void loadMore();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.listener=listener;
    }
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isShowLoadMoreView(position))
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

                if (isShowLoadMoreView(position))
                {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }
    public void setFootView(View footView){
        this.footView=footView;
        loading_pb= (ProgressBar) footView.findViewById(R.id.loading_pb);
        loading_text= (TextView) footView.findViewById(R.id.loading_text);
    }
    public void showLoadFail(){
        loading_text.setText("加载失败,点我重试");
        loading_pb.setVisibility(View.GONE);
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    reSetLoading();
                    listener.loadMore();
                }
            }
        });
    }
    public void reSetLoading(){
        loading_text.setText("加载中。。。");
        loading_pb.setVisibility(View.VISIBLE);
    }



}
