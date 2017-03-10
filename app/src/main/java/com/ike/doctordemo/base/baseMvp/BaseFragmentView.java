package com.ike.doctordemo.base.baseMvp;

/**
作者：ike
时间：2017/3/10 17:36
功能描述：fragment的view层的基类
**/

public  interface  BaseFragmentView extends BaseView{
    /**
     * 刷新数据接口
     * @param o
     */
    void OnReFresh(Object o);

    /**
     * 加载更多的数据接口
     * @param o
     */
    void OnLoadMore(Object o);
}
