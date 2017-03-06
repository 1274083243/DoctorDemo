package com.ike.doctordemo.widget.appBottomBarLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ike.doctordemo.R;

import java.util.ArrayList;
import java.util.List;

/**
作者：ike
时间：2017/3/6 10:51
功能描述：底部通用导航栏
**/
public class AppBottomBarLayout extends LinearLayout implements View.OnClickListener {
    private String Tag="AppBottomBarLayout";
    private int curr_choose_position=0;//当前选中的Item，默认是第一个
    private List<BottomBarItem> items;//底部导航选项的集合
    private int selectedTextColor;//item选中时的文本颜色
    private Context mContext;
    public AppBottomBarLayout(Context context) {
        this(context,null);
    }

    public AppBottomBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AppBottomBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        setClipChildren(false);
        setGravity(Gravity.BOTTOM);
        init();
    }
    private void init(){
        items=new ArrayList<>();
    }

    /**
     * 添加itemView到当前容器中
     */
    private void additemViewToParent() {
        if (!items.isEmpty()){
            for (int i=0;i<items.size();i++) {
                BottomBarItem item=items.get(i);
                LinearLayout itemView= (LinearLayout) View.inflate(mContext, R.layout.item_bottombar_layout,null);
                itemView.setId(i);
                LinearLayout.LayoutParams layoutParams;
                layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.weight=1;
                itemView.setLayoutParams(layoutParams);
                item.itemView=itemView;
                ImageView iv_icon= item.getView(R.id.iv_bar_icon);
                TextView tv_name= item.getView(R.id.tv_bar_name);
                iv_icon.setImageResource(item.normalIcon);
                tv_name.setText(item.text);
                itemView.setOnClickListener(this);
                addView(itemView);
            }
        }

    }

    /**
     * 添加item选项
     * @param item
     * @return
     */
    public AppBottomBarLayout addItem(BottomBarItem item){
        items.add(item);
        return  this;
    }

    /**
     * 设置文本选中颜色
     * @param color
     * @return
     */
    public AppBottomBarLayout setSelectedTextColor(int color){
        this.selectedTextColor=color;
        return this;
    }
    public AppBottomBarLayout build(){
        additemViewToParent();
        changeItemView(curr_choose_position);
        return  this;
    }
    @Override
    public void onClick(View v) {
        //重复点击判断：如果当前点击的条目等于当前的选中条目，则不做任何处理，直接返回
        if (v.getId()==curr_choose_position){
            return;
        }
        for (int i=0;i<items.size();i++){
            if (v.getId()==i){
                curr_choose_position=i;
            }
        }
        changeItemView(curr_choose_position);
        if (listener!=null){
            listener.onSelectedPositionChanged(curr_choose_position);
        }
    }
    /**
     * 修改view的图层显示状态
     * @param curr_choose_position
     */
    private void changeItemView(int curr_choose_position) {
        for (int i=0;i<items.size();i++){
            BottomBarItem item=items.get(i);
            if (curr_choose_position==i){
                ((TextView)item.getView(R.id.tv_bar_name)).setTextColor(selectedTextColor);
                ((ImageView) item.getView(R.id.iv_bar_icon)).setImageResource(item.selectedIcon);
            }else {
                ((ImageView) item.getView(R.id.iv_bar_icon)).setImageResource(item.normalIcon);
                ((TextView)item.getView(R.id.tv_bar_name)).setTextColor(getResources().getColor(R.color.gray_20));
            }
        }
    }

    private OnSelectedPositionChangedListener listener;
    public interface OnSelectedPositionChangedListener{
        void onSelectedPositionChanged(int position);
    }
    public void setOnSelectedPositionChangedListener(OnSelectedPositionChangedListener listener){
        this.listener=listener;
    }


}
