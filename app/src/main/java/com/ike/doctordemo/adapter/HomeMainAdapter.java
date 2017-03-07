package com.ike.doctordemo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ike.commonutils.recycleViewUtils.CommonAdapter;
import com.ike.commonutils.recycleViewUtils.ViewHolder;
import com.ike.doctordemo.DoctorApplication;
import com.ike.doctordemo.R;

import com.ike.doctordemo.model.HomeItem;

import java.util.List;

/**
 * 作者：ike
 * 时间：2017/3/7 9:36
 * 功能描述:首页数据适配器
 **/

public class HomeMainAdapter extends CommonAdapter<HomeItem> {
    public HomeMainAdapter(List<HomeItem> mData, int viewId) {
        super(mData, viewId);
    }

    @Override
    public void setData(ViewHolder holder, int position, HomeItem homeItem) {
        holder.setImageResource(R.id.item_main_image, Integer.valueOf(homeItem.iconUrl));
        holder.setTextColor(R.id.item_main_title, DoctorApplication.context.getResources().getColor(Integer.valueOf(homeItem.textColor)));
        holder.setText(R.id.item_main_title, homeItem.text);
    }
}
