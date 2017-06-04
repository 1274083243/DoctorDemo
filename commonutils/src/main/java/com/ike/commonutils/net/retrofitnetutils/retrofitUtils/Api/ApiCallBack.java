package com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api;


import com.ike.commonutils.net.retrofitnetutils.Exception.ApiException;
/**
 * Created by ike on 2017/2/27.
 * 请求结果回调函数
 */

public abstract class ApiCallBack<T> {
    public abstract void onSuccess(T result);
    public abstract void onError(ApiException e);


}
