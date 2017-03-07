package com.ike.doctordemo.model;

import android.util.Log;

import com.ike.doctordemo.DoctorApplication;
import com.ike.doctordemo.R;

import java.util.ArrayList;
import java.util.List;

/**
作者：ike
时间：2017/3/7 9:37
功能描述：首页单元格数据实体
**/
public class HomeItem {
    private static String Tag="HomeItem";
    public String iconUrl;//图片资源路径
    public String text;//文本描述内容
    public boolean isUnread;//标记是否有未读信息
    public String textColor;//文本显示颜色
    private static List<HomeItem> items=new ArrayList<>();
    public HomeItem(String iconUrl,String textColor, String text, boolean isUnread) {
        this.iconUrl = iconUrl;
        this.isUnread = isUnread;
        this.text = text;
        this.textColor=textColor;
    }
   public static List<HomeItem> getData(){
       items.clear();
       items.add(new HomeItem(R.drawable.icon_main_apply+"", R.color.marine_blue+"","申请记录", false));
       items.add(new HomeItem(R.drawable.icon_main_addmiss+"", R.color.blue_main_theme+"", "新增病例", false));
       items.add(new HomeItem(R.drawable.icon_main_draft+"", R.color.khaki+"", "接诊记录", false));
       items.add(new HomeItem(R.drawable.icon_main_doctor+"", R.color.green+"","找医生", false));
       return items;
   }
}
