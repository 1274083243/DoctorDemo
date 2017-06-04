package com.ike.commonutils.net.retrofitnetutils.download.download;

import android.content.Context;
import android.util.Log;

import com.ike.commonutils.net.retrofitnetutils.Exception.ApiException;
import com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api.BaseSubScriber;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
作者：ike
时间：2017/2/28 15:08
功能描述：下载信息订阅者
**/

public class DownLoadSubScriber<T> extends BaseSubScriber<T> implements DownLoadProgressListener{
    private WeakReference<DownLoadListener> downLoadListener;
    private DownLoadInfo downLoadInfo;
    private final DownLoadManager instance;
    private String Tag="DownLoadSubScriber";

    public DownLoadSubScriber(Context context,DownLoadInfo downLoadInfo) {
        super(context);
        this.downLoadInfo=downLoadInfo;
        this.downLoadListener =new WeakReference<>(downLoadInfo.downLoadListener);
        instance = DownLoadManager.getInstance(mContext.get());
    }
    @Override
    public void onStart() {
        super.onStart();
        downLoadInfo.downLoadState= DownLoadManager.DownLoadState.START;
        if (downLoadListener.get()!=null){
            downLoadListener.get().onStart();
        }


    }

    @Override
    public void onCompleted() {
        downLoadInfo.downLoadState= DownLoadManager.DownLoadState.FINISH;
        if (downLoadListener.get()!=null){
            downLoadListener.get().onComplete();
           instance.removeDownLoadInfo(downLoadInfo);

        }


    }

    @Override
    public void onError(ApiException exception) {
        downLoadInfo.downLoadState= DownLoadManager.DownLoadState.ERROR;
        if (downLoadListener.get()!=null){
            downLoadListener.get().onError(exception);
            instance.removeDownLoadInfo(downLoadInfo);
        }
    }

    @Override
    public void onNext(T t) {
        if (downLoadListener.get()!=null){
            downLoadListener.get().onNext(t);
        }
    }

    @Override
    public void upDownProgress( long currnetLength, long countLength, boolean isFinish) {
        if(downLoadInfo.totalLength>countLength){
            currnetLength=downLoadInfo.totalLength-countLength+currnetLength;
        }else{
            downLoadInfo.totalLength=countLength;
        }

        downLoadInfo.readLength=currnetLength;
        if (downLoadListener.get()!=null){
            Observable.just(currnetLength).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            if (downLoadInfo.downLoadState!= DownLoadManager.DownLoadState.PAUSE){
                                downLoadInfo.downLoadState= DownLoadManager.DownLoadState.DOWN_LOADING;
                                downLoadListener.get().onDownLoad(aLong,downLoadInfo.totalLength);
                            }
                        }
                    });
        }
    }
}
