package com.ike.doctordemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ike.doctordemo.R;
import com.ike.doctordemo.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：ike
 * 时间：2017/3/6 16:27
 * 功能描述：首页
 **/

public class HomeFragment extends BaseFragment {
    @Bind(R.id.main_tab_left)
    TextView mainTabLeft;
    @Bind(R.id.main_tab_center)
    TextView mainTabCenter;
    @Bind(R.id.main_tab_right)
    TextView mainTabRight;
    @Bind(R.id.rl_main)
    RecyclerView rlMain;
    @Override
    public View getLayoutView() {
        return View.inflate(mContext, R.layout.fragment_home_view_layout, null);
    }



}
