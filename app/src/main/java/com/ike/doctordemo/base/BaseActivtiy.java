package com.ike.doctordemo.base;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.ike.commonutils.commonUtils.StatusBarCompat;
import butterknife.ButterKnife;
/**
作者：ike
时间：2017/3/6 9:35
功能描述：所有Activity的基类
**/
public abstract class BaseActivtiy extends AppCompatActivity{
    protected String Tag;//日志打印字符串
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tag=getComponentName().getShortClassName();
        setContentView(getLayoutId());
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
        StatusBarCompat.compat(this);
       // StatusBarUtil.transparencyBar(this);
        //StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.blue_main_theme));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
