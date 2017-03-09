package com.ike.commonutils.widget.dropMenu;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ike.commonutils.R;
import com.ike.commonutils.commonUtils.DensityUtil;

import java.util.List;

/**
作者：ike
时间：2017/3/8 10:40
功能描述：下拉筛选菜单
**/
public class DropMenuLayout extends LinearLayout implements View.OnClickListener {
    private String Tag="DropMenuLayout";
    private LinearLayout tabLayout;
    private View lineView;
    private FrameLayout contentLayout;
    private FrameLayout mPopuView;
    private Context mContext;
    private List<String> tabNams;
    private List<View> mPopuViews;
    private View maskView;
    private int current_position;//当前显示的条目
    private boolean isShowing;//当前弹窗是否在显示中
    public DropMenuLayout(Context context) {
        this(context,null);
    }

    public DropMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DropMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        mContext=context;
        init();
    }

    /**
     * 初始化容器控件信息
     */
    private void init() {
        //初始化顶部tab栏容器
        tabLayout = new LinearLayout(mContext);
        LayoutParams tabLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tabLayout.setLayoutParams(tabLayoutParams);
        tabLayout.setBackgroundColor(Color.BLUE);
        tabLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(tabLayout,0);
        //初始化下方的分割线
        lineView=new View(mContext);
        LayoutParams lineViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, 2);
        lineView.setLayoutParams(lineViewParams);
        lineView.setBackgroundColor(Color.RED);
        addView(lineView,1);
        //初始化内容容器
        contentLayout=new FrameLayout(mContext);
        LayoutParams contentLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentLayout.setLayoutParams(contentLayoutParams);
        addView(contentLayout,2);
    }
    /**
     * 添加tab栏标签item:一般的组合便是文本加图片
     */
    private void addTabs(String tab_lable,int i){
        TextView tv_tab=new TextView(mContext);
        tv_tab.setId(i);
        LayoutParams tabLayoutParams = new LayoutParams(0, DensityUtil.dip2px(mContext,30),1.0f);
        tv_tab.setLayoutParams(tabLayoutParams);
        tv_tab.setGravity(Gravity.CENTER);
        tv_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tv_tab.setText(tab_lable);
        tv_tab.setOnClickListener(this);
        tabLayout.addView(tv_tab);

    }

    /**
     * 初始化dropview的数据
     */
    public void buildDropContenView(List<String> tabNames,List<View> mPopuViews){
        this.tabNams=tabNames;
        this.mPopuViews=mPopuViews;
        for (int i=0;i<tabNames.size();i++){
            addTabs(tabNames.get(i),i);
        }
        //初始化弹窗背景
        maskView=new View(mContext);
        FrameLayout.LayoutParams maskviewLayoutParam=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        maskView.setLayoutParams(maskviewLayoutParam);
        maskView.setBackgroundColor(Color.parseColor("#55000000"));
        maskView.setVisibility(GONE);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closePoupContent();
            }
        });
        contentLayout.addView(maskView,0);
        //添加弹窗内容
        mPopuView=new FrameLayout(mContext);
        FrameLayout.LayoutParams param1=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  DensityUtil.dip2px(mContext,250));

        mPopuView.setLayoutParams(param1);
        mPopuView.setVisibility(GONE);
        contentLayout.addView(mPopuView,1);
        for (int i=0;i<mPopuViews.size();i++){
            FrameLayout.LayoutParams param=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopuViews.get(i).setLayoutParams(param);
            mPopuView.addView(mPopuViews.get(i),i);
        }

    }

    @Override
    public void onClick(View v) {
        for (int i=0;i<tabNams.size();i++){
            if (v.getId()==i){
                if (isShowing){
                    if (v.getId()==current_position){
                        closePoupContent();
                        return;
                    }
                }
                showPoupContent(i);
                break;
            }
        }
    }

    /**
     * 添加动画展开内容弹窗
     */
    private void showPoupContent(int index) {
        for (int i=0;i<mPopuViews.size();i++){
            if (i==index){
                mPopuViews.get(i).setVisibility(VISIBLE);
            }else {
                mPopuViews.get(i).setVisibility(GONE);
            }
        }
        mPopuView.setVisibility(VISIBLE);
        maskView.setVisibility(VISIBLE);
        if (!isShowing){

            TranslateAnimation translate=new TranslateAnimation(0,0,-mPopuView.getHeight(),0);
            translate.setDuration(200);
            translate.setFillAfter(true);

            AlphaAnimation alpha=new AlphaAnimation(0,1);
            alpha.setDuration(200);

            maskView.startAnimation(alpha);
            mPopuView.startAnimation(translate);
        }
        current_position=index;
        isShowing=true;
    }

    /**
     * 关闭内容弹窗
     */
    private void closePoupContent(){
        TranslateAnimation translate=new TranslateAnimation(0,0,0,-mPopuView.getHeight());
        translate.setDuration(200);
        translate.setFillAfter(true);

        AlphaAnimation alpha=new AlphaAnimation(1,0);
        alpha.setDuration(200);
        mPopuView.setVisibility(GONE);
        maskView.setVisibility(GONE);
        maskView.startAnimation(alpha);
        mPopuView.startAnimation(translate);
        current_position=0;
        isShowing=false;
    }
}
