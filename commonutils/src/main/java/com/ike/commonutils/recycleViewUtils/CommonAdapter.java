package com.ike.commonutils.recycleViewUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import java.util.List;

/**
 * 作者：ike
 * 时间：2017/3/7 9:23
 * 功能描述：通用的recycleView的adapter
 **/

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String Tag = "CommonAdapter";
    private List<T> mData;
    private int viewId;

    public CommonAdapter(List<T> mData, int viewId) {
        this.mData = mData;
        this.viewId = viewId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.createViewHolder(parent.getContext(),parent, viewId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        setData(mHolder, holder.getAdapterPosition(), mData.get(position));
    }

    @Override
    public int getItemCount() {
        if (mData != null && mData.size() > 0) {
            return mData.size();
        }
        return 0;
    }

    /**
     * 给adapter设置数据信息
     *
     * @param holder
     * @param position
     * @param t
     */
    public abstract void setData(ViewHolder holder, int position, T t);
}
