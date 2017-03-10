package com.ike.doctordemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ike.doctordemo.base.baseMvp.BaseFragmentView;
import com.ike.doctordemo.base.baseMvp.BasePresenter;
import com.ike.doctordemo.base.baseMvp.BaseView;

import butterknife.ButterKnife;

/**
作者：ike
时间：2017/3/6 16:17
功能描述：fragment的基类
**/

public abstract class BaseFragment<P extends BasePresenter<V>,V extends BaseFragmentView> extends Fragment implements BaseFragmentView{
    protected Activity mContext;
    protected String Tag;
    protected P mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=getLayoutView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        mPresenter=initPresenter();
        mPresenter.attachView((V) this);
        initData();
        initListener();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        mPresenter.detachView();
        super.onDestroyView();
    }

    /**
     * 获取子类布局信息
     * @return
     */
    public abstract View getLayoutView();
    public abstract void initData();
    public abstract void initListener();

    /**
     * 初始化代理者
     * @return
     */
    public abstract P initPresenter();
}
