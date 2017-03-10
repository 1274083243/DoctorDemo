package com.ike.doctordemo.base.baseMvp;

/**
作者：ike
时间：2017/3/10 17:09
功能描述：mvp:presenter代理者，进行与view展示层的绑定
**/

public interface Presenter<V extends BaseView> {
    /**
     * 进行绑定
     * @param baseView
     */
    public void attachView(V baseView);

    /**
     * 解除绑定
     */
    public void detachView();
}
