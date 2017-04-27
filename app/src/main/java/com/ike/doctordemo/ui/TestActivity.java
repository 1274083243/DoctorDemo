package com.ike.doctordemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ike.commonutils.widget.loopViewpager.LoopModel;
import com.ike.commonutils.widget.loopViewpager.LooperViewPager;
import com.ike.doctordemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/4/27.
 */

public class TestActivity extends AppCompatActivity {
    private LooperViewPager looper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        looper= (LooperViewPager) findViewById(R.id.looper);
        List<LoopModel> loopModels=new ArrayList<>();
        loopModels.add(new LoopModel(R.drawable.icon_main_addmiss+"","第一页"));
        loopModels.add(new LoopModel(R.drawable.icon_main_apply+"","第二页"));
        looper.getBuilder().build(loopModels).setOnItemClickListener(new LooperViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(LoopModel model, int postion) {
                Toast.makeText(TestActivity.this, "postion:"+postion, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
