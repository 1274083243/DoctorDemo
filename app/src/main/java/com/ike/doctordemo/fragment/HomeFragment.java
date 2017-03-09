package com.ike.doctordemo.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ike.commonutils.widget.loopViewpager.LoopModel;
import com.ike.commonutils.recycleViewUtils.HeadAndFootWrapper;
import com.ike.commonutils.widget.loopViewpager.LooperViewPager;
import com.ike.commonutils.recycleViewUtils.SpaceItemDecoration;
import com.ike.doctordemo.R;
import com.ike.doctordemo.adapter.HomeMainAdapter;
import com.ike.doctordemo.base.BaseFragment;
import com.ike.doctordemo.model.HomeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 作者：ike
 * 时间：2017/3/6 16:27
 * 功能描述：首页
 **/

public class HomeFragment extends BaseFragment {
    private String Tag="HomeFragment";
    @Bind(R.id.main_tab_left)
    TextView mainTabLeft;
    @Bind(R.id.main_tab_center)
    TextView mainTabCenter;
    @Bind(R.id.main_tab_right)
    TextView mainTabRight;
    @Bind(R.id.rl_main)
    RecyclerView rlMain;
    private LooperViewPager looperViewPager;
    @Override
    public View getLayoutView() {
        return View.inflate(mContext, R.layout.fragment_home_view_layout, null);
    }

    @Override
    public void initData() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rlMain.setLayoutManager(gridLayoutManager);
        rlMain.addItemDecoration(new SpaceItemDecoration(10, HomeItem.getData().size()));
        HomeMainAdapter homeMainAdapter = new HomeMainAdapter(HomeItem.getData(), R.layout.item_main_adapter_layout);
        HeadAndFootWrapper adapter=new HeadAndFootWrapper(homeMainAdapter);
        View headView = View.inflate(mContext, R.layout.head_fragment_home_view_layout, null);
        looperViewPager= (LooperViewPager) headView.findViewById(R.id.head_looper_vp);
        List<LoopModel> loopModels=new ArrayList<>();
        loopModels.add(new LoopModel(R.drawable.icon_main_addmiss+"","第一页"));
        loopModels.add(new LoopModel(R.drawable.icon_main_apply+"","第二页"));
        looperViewPager.getBuilder()
                .setAllowAutoLoop(true,2000)
                .build(loopModels);
        adapter.addHeadView(headView);
        rlMain.setAdapter(adapter);
    }

    @Override
    public void initListener() {

    }


}
