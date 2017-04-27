package com.ike.commonutils.widget.appBottomBarLayout;

import android.support.v4.util.SparseArrayCompat;
import android.view.View;

/**
作者：ike
时间：2017/3/6 10:57
功能描述：底部导航栏的数据实体类
**/

public class BottomBarItem {
    public int normalIcon;//未选中时的图标
    public int selectedIcon;//选中时的图片
    public String text;//item的文本描述
    public View itemView;//当前item的view布局
    private SparseArrayCompat<View> viewContainer=new SparseArrayCompat<>();//当前布局的控件容器
    public BottomBarItem(int normalIcon, int selectedIcon, String text) {
        this.normalIcon = normalIcon;
        this.selectedIcon = selectedIcon;
        this.text = text;
    }
    /**
     * 根据控件Id查找控件
     * @param viewId：控件id
     * @param <T>
     * @return
     */
    public  <T extends View> T getView(int viewId){
        View view = viewContainer.get(viewId);
        if (view==null){
            view=itemView.findViewById(viewId);
            viewContainer.put(viewId,view);
        }
        return (T) view;
    }


}
