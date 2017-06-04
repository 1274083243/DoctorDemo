package com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.ike.commonutils.BuildConfig;
import com.ike.commonutils.net.retrofitnetutils.TransformUtils.TransForm2Clazz;
import com.ike.commonutils.net.retrofitnetutils.commonUtils.ClassUtil;
import com.ike.commonutils.net.retrofitnetutils.commonUtils.WriteFileUtils;
import com.ike.commonutils.net.retrofitnetutils.download.download.DownLoadInfo;
import com.ike.commonutils.net.retrofitnetutils.download.download.DownLoadProgressListener;
import com.ike.commonutils.net.retrofitnetutils.download.download.DownLoadSubScriber;
import com.ike.commonutils.net.retrofitnetutils.intercepter.CommonParamIntercepter;
import com.ike.commonutils.net.retrofitnetutils.intercepter.DownLoadIntercepter;
import com.ike.commonutils.net.retrofitnetutils.intercepter.UploadProgressCallBack;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
作者：ike
时间：2017/2/27 17:09
功能描述：retrofit网络请求工具类
**/

public class RetrofitNetUtils {
    private static String Tag="RetrofitNetUtils";
    private static Context context;
    private static OkHttpClient.Builder okHttpBuilder;
    private static Retrofit.Builder retrofitBuilder;
    private static ApiService apiService;
    private static final int DEFALT_TIME=10;
    public static final String BASE_URL="https://qqb.sdblo.xyz:11443/";
    private RetrofitNetUtils(){

    }

    /**
     * 采用链式建造者模式
     */
    public static final class Builder{



        public Builder(Context mContext) {
            context = mContext.getApplicationContext();
            okHttpBuilder=new OkHttpClient.Builder();
            retrofitBuilder=new Retrofit.Builder();
        }

        /**
         * 添加下载拦截器
         * @param listener
         * @return
         */
        public Builder downLoadIntercepter(DownLoadProgressListener listener){
            okHttpBuilder.addInterceptor(new DownLoadIntercepter(listener));
            return this;
        }

        /**
         * 日志拦截器的添加：只有在LOG_DEBUG=true的情况下才会添加
         * @return
         */
        public Builder LogIntercepter(){
            if (BuildConfig.LOG_DEBUG){
                HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.e(Tag, message);
                    }
                });
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpBuilder.addInterceptor(loggingInterceptor);
            }
            return this;
        }
        public RetrofitNetUtils build(){

            OkHttpClient okHttpClient = okHttpBuilder
                    .connectTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .readTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .addInterceptor(new CommonParamIntercepter())
                    .build();
            Retrofit retrofit = retrofitBuilder
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient).build();
             apiService = retrofit.create(ApiService.class);
            return new RetrofitNetUtils();
        }


    }

    /**
     * 通用post请求封装
     * @return Subscription 订阅者
     */
    public <T> Subscription post(String url, Map<String,String> param, ApiCallBack<T> callBack){
        return apiService.post(url,param)
                .compose(this.normalTrans(ClassUtil.getTClass(callBack)))
                .subscribe(new ApiCallBackSubscriber(context, callBack));
    }
    /**
     * 通用get请求封装
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Subscription get(String url, Map<String,String> param, ApiCallBack<T> callBack){
        return apiService.get(url,param)
                .compose(this.normalTrans(ClassUtil.getTClass(callBack)))
                .subscribe(new ApiCallBackSubscriber(context, callBack));
    }


    /**
     * 通用文件上传
     * @param url
     * @param param
     * @param file
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Subscription upLoadFile(String url, Map<String,Object> param, MultipartBody.Part file, UploadProgressCallBack<T> callBack){
        return apiService.upLoadFile(url,param,file)
                .compose(this.normalTrans(ClassUtil.getTClass(callBack)))
                .subscribe(new ApiCallBackSubscriber(context, callBack));
    }
    public <T> Subscription uploadFileTest(String url, Map<String,Object> param, List<MultipartBody.Part> files, UploadProgressCallBack<T> callBack){
        return apiService.upLoadFileTest(url,param,files)
                .compose(this.normalTrans(ClassUtil.getTClass(callBack)))
                .subscribe(new ApiCallBackSubscriber(context, callBack));
    }

    /**
     * 文件断点下载
     * @param info：文件下载包装类
     * @param <T>
     * @return
     */

    public <T> Subscription downLoadFile(final DownLoadInfo info, DownLoadSubScriber downLoadSubScriber){
        return apiService.downLoadFile(info.downLoadPath,"bytes="+info.readLength+"-")
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody,DownLoadInfo>() {
                    @Override
                    public DownLoadInfo call(ResponseBody responseBody) {
                        try {
                            WriteFileUtils.writeCache(responseBody,new File(info.savePath),info);
                        } catch (Exception e) {

                        }

                        return info;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downLoadSubScriber);

    }

    /**
     * 数据类型转换器：ResponseBody类型数据转换为我们使用的T类型数据
     * @param clazz：需要转换为的class类型
     * @param <T>
     * @return
     */
    public <T> Observable.Transformer<ResponseBody,T> normalTrans(final Class<T> clazz){
        return new Observable.Transformer<ResponseBody, T>(){
            @Override
            public Observable<T> call(Observable<ResponseBody> responseBodyObservable) {
                return responseBodyObservable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new TransForm2Clazz(clazz));
            }
        };
    }
}
