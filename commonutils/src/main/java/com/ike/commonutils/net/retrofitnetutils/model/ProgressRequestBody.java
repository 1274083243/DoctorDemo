package com.ike.commonutils.net.retrofitnetutils.model;

import android.nfc.Tag;
import android.util.Log;

import com.ike.commonutils.net.retrofitnetutils.Exception.ApiCode;
import com.ike.commonutils.net.retrofitnetutils.Exception.ApiException;
import com.ike.commonutils.net.retrofitnetutils.intercepter.UploadProgressCallBack;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
作者：ike
时间：2017/2/28 11:29
功能描述：上传进度请求实体
**/
public class ProgressRequestBody<T> extends RequestBody{
    //实际的待包装请求体
    private final RequestBody requestBody;
    private  List<MultipartBody.Part> parts=null;
    //进度回调接口
    private UploadProgressCallBack<T> progressCallBack;
    //包装完成的BufferedSink
    private BufferedSink bufferedSink;

    public ProgressRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
    public void setParts(List<MultipartBody.Part> parts){
        this.parts=parts;

    }

    public void setProgressCallBack(UploadProgressCallBack<T> progressCallBack){
        this.progressCallBack=progressCallBack;
    }
    /**
     * 重写调用实际的响应体的contentType
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }
    /**
     * 重写调用实际的响应体的contentLength
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        long contentLength;

            contentLength=requestBody.contentLength();

        return contentLength;
    }
    /**
     * 重写进行写入
     * @param sink BufferedSink
     * @throws IOException 异常
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (null == bufferedSink) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }
    /**
     * 写入，回调进度接口
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long writtenBytesCount = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long totalBytesCount = 0L;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                //增加当前写入的字节数
                writtenBytesCount += byteCount;
                //获得contentLength的值，后续不再调用
                if (totalBytesCount == 0) {
                    totalBytesCount = contentLength();
                }
                Observable.just(writtenBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        progressCallBack.onError(new ApiException(e, ApiCode.Request.UNKNOWN));

                    }

                    @Override
                    public void onNext(Long aLong) {
                        progressCallBack.onProgress(writtenBytesCount,totalBytesCount);

                    }
                });
            }
        };
    }
}
