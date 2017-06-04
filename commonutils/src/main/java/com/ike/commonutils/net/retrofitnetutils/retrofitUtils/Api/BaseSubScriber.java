package com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api;

import android.content.Context;
import android.widget.Toast;

import com.ike.commonutils.net.retrofitnetutils.Exception.ApiCode;
import com.ike.commonutils.net.retrofitnetutils.Exception.ApiException;
import com.ike.commonutils.net.retrofitnetutils.Exception.NetWorkException;
import com.ike.commonutils.net.retrofitnetutils.commonUtils.NetworkUtils;

import java.lang.ref.WeakReference;
import rx.Subscriber;

/**
 * 所有订阅者的基类，先一步进行网络判断与错误处理
 */

public abstract class BaseSubScriber<T> extends Subscriber<T> {
    private String Tag="BaseSubScriber";
    protected WeakReference<Context> mContext;

    public BaseSubScriber(Context context) {
        mContext=new WeakReference<Context>(context);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected(mContext.get())){
            onError(new ApiException(new NetWorkException(), ApiCode.Request.NETWORK_ERROR));
            Toast.makeText(mContext.get(),"没有网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException){
            onError(e);
        }else {
            onError(new ApiException(e, ApiCode.Request.UNKNOWN));
        }
    }
    public abstract void onError(ApiException exception);
}
