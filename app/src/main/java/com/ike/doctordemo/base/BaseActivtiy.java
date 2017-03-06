package com.ike.doctordemo.base;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ike.commonutils.commonUtils.StatusBarCompat;
import com.ike.commonutils.commonUtils.StatusBarUtil;
import com.ike.doctordemo.R;

import butterknife.ButterKnife;
/**
作者：ike
时间：2017/3/6 9:35
功能描述：所有Activity的基类
**/
public abstract class BaseActivtiy extends AppCompatActivity{
    protected String Tag;//日志打印字符串
    protected View baseTitleView;//通用头部导航栏布局
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tag=getComponentName().getShortClassName();
        setContentView(initBaseView());
        ButterKnife.bind(this);
        initTitleBarColor();
        initView();
        initListener();
        initData();
    }
    /**
     * 获取页面显示布局
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化UI控件信息
     * @return
     */
    public void initView(){

    };

    /**
     * 初始化事件监听
     */
    public abstract void initListener();

    /**
     * 初始化数据：如联网请求数据等
     */
    public abstract void initData();
    //初始化状态栏颜色
    public void initTitleBarColor(){
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.setStatusBarColor(this,R.color.blue_main_theme);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    protected LinearLayout btn_left;//左边的返回键
    protected TextView tv_title_name;//界面名称
    private FrameLayout content_container;//内容容器
    /**
     * 初始化baseView的布局文件
     */
    public View initBaseView(){
        View baseView=View.inflate(this, R.layout.common_title_bar_layout,null);
        btn_left= (LinearLayout) baseView.findViewById(R.id.btn_left);
        tv_title_name= (TextView) baseView.findViewById(R.id.tv_title_name);
        baseTitleView=baseView.findViewById(R.id.title_bar_container);
        content_container= (FrameLayout) baseView.findViewById(R.id.content_container);
        content_container.removeAllViews();
        View content_view=View.inflate(this,getLayoutId(),null);
        content_container.addView(content_view);
        return baseView;
    }
}
