package com.ike.doctordemo.ui;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.ike.doctordemo.R;
import com.ike.doctordemo.base.BaseActivtiy;
import com.ike.doctordemo.base.baseMvp.Presenter;
import com.ike.doctordemo.fragment.HomeFragment;
import com.ike.commonutils.widget.appBottomBarLayout.AppBottomBarLayout;
import com.ike.commonutils.widget.appBottomBarLayout.BottomBarItem;

import butterknife.Bind;


public class MainActivity extends BaseActivtiy {
    @Bind(R.id.bottom_layout)
    AppBottomBarLayout bottomLayout;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        baseTitleView.setVisibility(View.GONE);
    }
    @Override
    public void initData() {
        initBottomBarLayout();
        fragmentManager = getSupportFragmentManager();
        homeFragment=new HomeFragment();
        fragmentManager.beginTransaction().add(R.id.main_content_container,homeFragment).commit();
    }
    @Override
    public void initListener() {

    }

    @Override
    public Presenter initPresenter() {
        return null;
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomBarLayout() {
        bottomLayout.addItem(new BottomBarItem(R.drawable.icon_tab_home_nor, R.drawable.icon_tab_home_pre, "首页"))
                .addItem(new BottomBarItem(R.drawable.icon_tab_union_nor, R.drawable.icon_tab_union_pre, "转诊联盟"))
                .addItem(new BottomBarItem(R.drawable.icon_tab_case_nor, R.drawable.icon_tab_case_pre, "病例库"))
                .addItem(new BottomBarItem(R.drawable.icon_tab_mine_nor, R.drawable.icon_tab_mine_pre, "我的"))
                .setSelectedTextColor(getResources().getColor(R.color.blue_main_theme))
                .build();
        bottomLayout.setOnSelectedPositionChangedListener(new AppBottomBarLayout.OnSelectedPositionChangedListener() {
            @Override
            public void onSelectedPositionChanged(int position) {
                Toast.makeText(MainActivity.this, "点击了:" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
