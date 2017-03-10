package com.ike.doctordemo.base.baseMvp;

/**
作者：ike
时间：2017/3/10 17:14
功能描述：代理者的基类
**/
public class BasePresenter<V extends BaseView> implements Presenter<V>{
    protected V view;
    @Override
    public void attachView(V baseView) {
        this.view=baseView;
    }

    @Override
    public void detachView() {
        view=null;
    }
}
