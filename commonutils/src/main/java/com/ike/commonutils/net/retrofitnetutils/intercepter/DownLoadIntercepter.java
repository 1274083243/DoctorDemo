package com.ike.commonutils.net.retrofitnetutils.intercepter;

import android.util.Log;

import com.ike.commonutils.net.retrofitnetutils.download.download.DownLoadProgressListener;
import com.ike.commonutils.net.retrofitnetutils.download.download.DownloadResponseBody;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;
/**
* author ike
* create time 15:23 2017/5/20
* function:下载拦截器
**/

public class DownLoadIntercepter implements Interceptor{
    private DownLoadProgressListener downLoadProgressListener;

    public DownLoadIntercepter(DownLoadProgressListener downLoadProgressListener) {
        this.downLoadProgressListener = downLoadProgressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(new DownloadResponseBody(response.body(),downLoadProgressListener)).build();
    }
}
