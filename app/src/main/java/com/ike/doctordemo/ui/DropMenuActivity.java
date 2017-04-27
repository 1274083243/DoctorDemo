package com.ike.doctordemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ike.doctordemo.widget.courseTableLayout.CourseTableLayout;
import com.ike.commonutils.widget.dropMenu.DropMenuLayout;
import com.ike.doctordemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/8.
 */

public class DropMenuActivity extends AppCompatActivity {
//    @Bind(R.id.drop_menu)
//    DropMenuLayout dropMenu;
//    @Bind(R.id.corse_menu)
//    CourseTableLayout corseMenu;
//    private List<String> tabNames = new ArrayList<>();
//    private List<View> mPopuViews = new ArrayList<>();
//    private List<String> mdateData=new ArrayList<>();
//    private List<String> corseData=new ArrayList<>();
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//       // setContentView(R.layout.activity_drop_menu_layout);
//        ButterKnife.bind(this);
//        tabNames.add("页面1");
//        tabNames.add("页面2");
//        tabNames.add("页面3");
//        mPopuViews.add(initView1());
//        mPopuViews.add(initView2());
//        mPopuViews.add(initView3());
//        mdateData.add("星期一");
//        mdateData.add("星期二");
//        mdateData.add("星期三");
//        mdateData.add("星期四");
//        mdateData.add("星期五");
//        mdateData.add("星期六");
//        mdateData.add("星期天");
//        for (int i=0;i<24;i++){
//            if (i==0){
//                corseData.add("上午");
//               continue;
//            }
//            if (i==8){
//                corseData.add("下午");
//                continue;
//            }
//            if (16==i){
//                corseData.add("晚上");
//                continue;
//            }
//            corseData.add(""+i);
//        }
//        dropMenu.buildDropContenView(tabNames, mPopuViews);
//        corseMenu.buildCourseTableLayout(mdateData,corseData);
//    }
//
//    public View initView1() {
//        List<String> mdata = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            mdata.add("我是页面一，条目：" + i);
//        }
//        ListView listView = new ListView(this);
//        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.test_item_layout, mdata));
//        return listView;
//    }
//
//    public View initView2() {
//        List<String> mdata = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            mdata.add("我是页面2，条目：" + i);
//        }
//        ListView listView = new ListView(this);
//        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.test_item_layout, mdata));
//        return listView;
//    }
//
//    public View initView3() {
//        List<String> mdata = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            mdata.add("我是页面3，条目：" + i);
//        }
//        ListView listView = new ListView(this);
//        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.test_item_layout, mdata));
//        return listView;
//    }
}
