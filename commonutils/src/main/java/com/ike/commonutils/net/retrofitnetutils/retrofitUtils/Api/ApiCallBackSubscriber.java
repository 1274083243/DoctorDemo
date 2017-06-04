package com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api;

import android.content.Context;

import com.ike.commonutils.net.retrofitnetutils.Exception.ApiException;


/**
 * Created by ike on 2017/2/27.
 * 接口回调订阅
 */

public class ApiCallBackSubscriber<T> extends BaseSubScriber<T> {
    protected  ApiCallBack<T> mCallBack;
    private String Tag="ApiCallBackSubscriber";
    public ApiCallBackSubscriber(Context context, ApiCallBack<T> callBack) {
        super(context);
        this.mCallBack=callBack;
    }

    @Override
    public void onError(ApiException exception) {
        mCallBack.onError(exception);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        mCallBack.onSuccess(t);
    }


}
