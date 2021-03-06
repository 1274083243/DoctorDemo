package com.ike.commonutils.net.retrofitnetutils.intercepter;


import com.ike.commonutils.net.retrofitnetutils.model.ProgressRequestBody;
import com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api.ApiCallBack;

/**
作者：ike
时间：2017/2/28 11:32
功能描述：上传进度监听
**/

public abstract class UploadProgressCallBack<T> extends ApiCallBack<T> {
    public UploadProgressCallBack(ProgressRequestBody<T> body) {
        body.setProgressCallBack(this);
    }
    public abstract void onProgress(Long current, Long total);

}
