package com.ike.doctordemo;

import android.os.Bundle;
import android.widget.Toast;

import com.ike.doctordemo.base.BaseActivtiy;
import com.ike.doctordemo.widget.appBottomBarLayout.AppBottomBarLayout;
import com.ike.doctordemo.widget.appBottomBarLayout.BottomBarItem;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivtiy {

    @Bind(R.id.bottom_layout)
    AppBottomBarLayout bottomLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initListener() {

    }
    @Override
    public void initData() {
        bottomLayout.addItem(new BottomBarItem(R.drawable.icon_tab_home_nor,R.drawable.icon_tab_home_pre,"首页"))
                .addItem(new BottomBarItem(R.drawable.icon_tab_union_nor,R.drawable.icon_tab_union_pre,"转诊联盟"))
                .addItem(new BottomBarItem(R.drawable.icon_tab_case_nor,R.drawable.icon_tab_case_pre,"病例库"))
                .addItem(new BottomBarItem(R.drawable.icon_tab_mine_nor,R.drawable.icon_tab_mine_pre,"我的"))
                .setSelectedTextColor(getResources().getColor(R.color.blue_main_theme))
                .build();
        bottomLayout.setOnSelectedPositionChangedListener(new AppBottomBarLayout.OnSelectedPositionChangedListener() {
            @Override
            public void onSelectedPositionChanged(int position) {
                Toast.makeText(MainActivity.this,"点击了:"+(position+1),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
