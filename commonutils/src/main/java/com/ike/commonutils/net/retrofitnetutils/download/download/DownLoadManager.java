package com.ike.commonutils.net.retrofitnetutils.download.download;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api.RetrofitNetUtils;
import com.ike.commonutils.net.retrofitnetutils.threadManager.ThreadManager;
import com.ike.commonutils.net.retrofitnetutils.threadManager.ThreadPoolProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者：ike
 * 时间：2017/2/28 14:58
 * 功能描述：下载管理类
 **/

public class DownLoadManager {
    private String Tag="DownLoadManager";
    private static volatile DownLoadManager INTANCE;
    private static Context mContext;
    private Map<String, DownLoadSubScriber> downLoadSubScriberMap = new HashMap<>();//下载观察者集合
    private List<DownLoadInfo> downLoadInfos = new ArrayList<>();//下载对象集合
    private ThreadPoolProxy threadManager;
    private Map<String,DownLoadTask> tasks=new HashMap<>();
    /**
     * 同时最大下载数目
     */
    private int maxDownLoadNum=1;
    public void setMaxDownLoadNum(int maxDownLoadNum) {
        this.maxDownLoadNum = maxDownLoadNum;
    }
    public int getMaxDownLoadNum() {
        return maxDownLoadNum;
    }

    public enum DownLoadState {
        START,//开始
        PAUSE,//暂停
        ERROR,//下载出错
        DOWN_LOADING,//下载
        FINISH,//xi下载完成
        WAIT//等待下载
    }

    private DownLoadManager() {
        threadManager=ThreadManager.getDownloadPool();
    }

    /**
     * 创建DownLoadManager单例
     *
     * @return
     */
    public static DownLoadManager getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (INTANCE == null) {
            synchronized (DownLoadManager.class) {
                if (INTANCE == null) {
                    INTANCE = new DownLoadManager();
                }
            }
        }
        return INTANCE;
    }

    /**
     * 开始下载
     */
    public void startDownLoad(DownLoadInfo downInfo, DownLoadListener listener) {
        if (downInfo==null){
            return;
        }

        if (!tasks.containsKey(downInfo.id)){
            if (tasks.size()>=maxDownLoadNum){
                Toast.makeText(mContext, "最大同时下载任务数目是"+maxDownLoadNum+"个，请等待", Toast.LENGTH_SHORT).show();
                return;
            }
            DownLoadTask task=new DownLoadTask(downInfo, listener);
            tasks.put(downInfo.id,task);
            threadManager.execute(task);
        }
    }

    /**
     * 开启下载任务
     * @param downInfo
     * @param listener
     */
    private void openDownLoadTask(DownLoadInfo downInfo, DownLoadListener listener) {
        if (listener!=null){
            downInfo.downLoadListener = listener;
        }
        if (!downLoadInfos.contains(downInfo)) {
            downLoadInfos.add(downInfo);
        }
        DownLoadSubScriber subScriber = new DownLoadSubScriber(mContext, downInfo);
        downLoadSubScriberMap.put(downInfo.id, subScriber);
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(mContext)
                .downLoadIntercepter(subScriber)
                .build();
        retrofitNetUtils.downLoadFile(downInfo, subScriber);
    }

    /**
     * 继续下载
     */
    public void goOnDownLoad(DownLoadInfo downInfo) {
        if (downInfo==null){
            return;
        }
        DownLoadSubScriber subScriber = new DownLoadSubScriber(mContext, downInfo);
        downLoadSubScriberMap.put(downInfo.id, subScriber);
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(mContext)
                .downLoadIntercepter(subScriber)
                .build();
        retrofitNetUtils.downLoadFile(downInfo, subScriber);
    }

    /**
     * 暂停下载
     *
     * @param downInfo
     */
    public void pauseDownLoad(DownLoadInfo downInfo) {
        if (downInfo == null) {
            return;
        }
        downInfo.downLoadState = DownLoadState.PAUSE;
        downInfo.downLoadListener.onPause();
        DownLoadSubScriber scriber = downLoadSubScriberMap.get(downInfo.id);
        if (scriber != null) {
            scriber.unsubscribe();
            scriber=null;
            downLoadSubScriberMap.remove(scriber);
        }
    }

    /**
     * 移除一个下载信息
     *
     * @param info
     */
    public void removeDownLoadInfo(DownLoadInfo info) {
        if (info != null) {
            downLoadInfos.remove(info);
            downLoadSubScriberMap.remove(info.id);
            tasks.remove(info.id) ;
        }
    }

    /**
     * 下载文物
     */
    class DownLoadTask implements Runnable{
       private DownLoadInfo downInfo;
        private DownLoadListener listener;

        public DownLoadTask(DownLoadInfo downInfo,DownLoadListener listener) {
            this.downInfo=downInfo;
            this.listener=listener;
            downInfo.downLoadState=DownLoadState.WAIT;
            listener.onWaiting();
        }
        @Override
        public void run() {
            openDownLoadTask(downInfo,listener);
        }
    }
}
