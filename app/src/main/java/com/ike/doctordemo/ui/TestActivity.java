package com.ike.doctordemo.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ike.commonutils.UiBase.BaseActivity;
import com.ike.commonutils.net.retrofitnetutils.Exception.ApiException;
import com.ike.commonutils.net.retrofitnetutils.download.download.DownLoadInfo;
import com.ike.commonutils.net.retrofitnetutils.download.download.DownLoadManager;
import com.ike.commonutils.net.retrofitnetutils.download.download.DownLoadListener;
import com.ike.commonutils.net.retrofitnetutils.intercepter.UploadProgressCallBack;
import com.ike.commonutils.net.retrofitnetutils.model.ExtendsApiResult;
import com.ike.commonutils.net.retrofitnetutils.model.ProgressRequestBody;
import com.ike.commonutils.net.retrofitnetutils.model.model;
import com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api.ApiCallBack;
import com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api.RetrofitNetUtils;
import com.ike.commonutils.net.retrofitnetutils.threadManager.ThreadManager;
import com.ike.doctordemo.R;
import com.ike.doctordemo.databinding.ActivityMain2Binding;
import com.ike.doctordemo.view.BezierPathDemo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

/**
 * Created by dell on 2017/4/27.
 */

public class TestActivity extends BaseActivity implements View.OnClickListener {
    private String Tag = "MainActivity";
    private Button upLoad;
    private Button downLoad, downLoad_pause, downLoad_goon;
    private Button downLoad2, downLoad_pause2, downLoad_goon2, test;
    private final int IMAGE = 1;
    private TextView tv_pg, tv_d_pg, tv_d_pg2;
    private DownLoadInfo downInfo;
    private DownLoadInfo downInfo2;
    private BezierPathDemo bezier_view;
    ActivityMain2Binding viewDataBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main2);


        bezier_view = (BezierPathDemo) findViewById(R.id.bezier_view);

        upLoad = (Button) findViewById(R.id.upLoad);
        test = (Button) findViewById(R.id.test);
        tv_pg = (TextView) findViewById(R.id.tv_pg);
        tv_d_pg2 = (TextView) findViewById(R.id.tv_d_pg2);
        downLoad = (Button) findViewById(R.id.downLoad);
        downLoad_pause = (Button) findViewById(R.id.downLoad_pause);
        downLoad_goon = (Button) findViewById(R.id.downLoad_goon);

        downLoad2 = (Button) findViewById(R.id.downLoad2);
        downLoad_pause2 = (Button) findViewById(R.id.downLoad_pause2);
        downLoad_goon2 = (Button) findViewById(R.id.downLoad_goon2);

        tv_d_pg = (TextView) findViewById(R.id.tv_d_pg);
        upLoad.setOnClickListener(this);
        downLoad.setOnClickListener(this);
        downLoad_pause.setOnClickListener(this);
        downLoad_goon.setOnClickListener(this);

        downLoad2.setOnClickListener(this);
        downLoad_pause2.setOnClickListener(this);
        downLoad_goon2.setOnClickListener(this);
        test.setOnClickListener(this);
        List<String> permission = new ArrayList<>();
        permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        checkAndRequestPermission(permission);
    }

    public void start_animation(View view) {
      viewDataBinding.wave.startAnimation();
        //bezier_view.startAnimator();
    }

    public void testGet() {
        Map<String, String> map = new HashMap<>();
        map.put("ps", 5 + "");
        map.put("pv", 0 + "");
        map.put("time", 0 + "");
        map.put("pt", "gt");
        map.put("op", 601 + "");
        Map<String, String> map1 = new HashMap<>();
        map1.put("data", JSON.toJSONString(map));
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(this).build();
        retrofitNetUtils.post("http://qqb.sdblo.xyz:10002/index.supreme", map1, new ApiCallBack<ExtendsApiResult<model>>() {
            @Override
            public void onSuccess(ExtendsApiResult<model> result) {
                Log.e(Tag, "result:" + result);
            }

            @Override
            public void onError(ApiException e) {
                Log.e(Tag, "出错了:" + e.getMessage() + "，code:" + e.getCode());
            }


        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upLoad:
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                break;
            case R.id.downLoad:
                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "test" + System.currentTimeMillis() + ".apk");
                Log.e(Tag, "outputFile:" + outputFile.getAbsolutePath());
                downInfo = new DownLoadInfo();
                downInfo.id = UUID.randomUUID().toString();
                downInfo.savePath = outputFile.getAbsolutePath();
                downInfo.downLoadPath = "http://www.izaodao.com/app/izaodao_app.apk";
                downInfo.downLoadState = DownLoadManager.DownLoadState.START;
                downLoad1(downInfo);
                break;
            case R.id.downLoad_pause:
                DownLoadManager.getInstance(this).pauseDownLoad(downInfo);
                break;
            case R.id.downLoad_goon:
                DownLoadManager.getInstance(this).goOnDownLoad(downInfo);
                break;
            case R.id.downLoad2:
                File outputFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "test" + System.currentTimeMillis() + ".apk");
                Log.e(Tag, "outputFile:" + outputFile1.getAbsolutePath());
                downInfo2 = new DownLoadInfo();
                downInfo2.id = UUID.randomUUID().toString();
                downInfo2.savePath = outputFile1.getAbsolutePath();
                downInfo2.downLoadPath = "http://www.izaodao.com/app/izaodao_app.apk";
                downInfo2.downLoadState = DownLoadManager.DownLoadState.START;
                downLoad2(downInfo2);
                break;
            case R.id.downLoad_pause2:
                DownLoadManager.getInstance(this).pauseDownLoad(downInfo2);
                break;
            case R.id.downLoad_goon2:
                DownLoadManager.getInstance(this).goOnDownLoad(downInfo2);
                break;
            case R.id.test:
                doTask();
                break;
        }

    }

    public void doTask() {
        //线程池去执行15个任务
        for (int i = 0; i < 4; i++) {
            File outputFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "test" + System.currentTimeMillis() + ".apk");
            Log.e(Tag, "outputFile:" + outputFile1.getAbsolutePath());
            downInfo2 = new DownLoadInfo();
            downInfo2.id = UUID.randomUUID().toString();
            downInfo2.savePath = outputFile1.getAbsolutePath();
            downInfo2.downLoadPath = "http://www.izaodao.com/app/izaodao_app.apk";
            downInfo2.downLoadState = DownLoadManager.DownLoadState.START;
            downLoad2(downInfo2);
//            MyRunnable myRunnable = new MyRunnable(i);
//            ThreadManager.getDownloadPool().execute(myRunnable);
//            System.out.println("线程池中现在的线程数目是："+ ThreadManager.getDownloadPool().getmExecutor().getPoolSize()+",  队列中正在等待执行的任务数量为："+
//                    ThreadManager.getDownloadPool().getmExecutor().getQueue().size());
        }
    }

    public class MyRunnable implements Runnable {

        // 正在执行的任务数
        private int num;

        public MyRunnable(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println("正在执行的MyRunnable " + num);
            try {
                Thread.currentThread().sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MyRunnable " + num + "执行完毕");

        }

    }

    /**
     * 文件下载
     *
     * @param downInfo
     */
    private void downLoad1(DownLoadInfo downInfo) {
        DownLoadListener downLoadListener = new DownLoadListener<DownLoadInfo>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPause() {
                Log.e(Tag, "onPause");
            }

            @Override
            public void onNext(DownLoadInfo s) {

            }

            @Override
            public void onDownLoad(long currentLength, long countLength) {
                tv_d_pg.setText(String.valueOf(currentLength * 1.0f / countLength * 100));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onWaiting() {
                tv_d_pg.setText("等待中...");
            }
        };

        DownLoadManager.getInstance(this).startDownLoad(downInfo, downLoadListener);
    }

    /**
     * 文件下载
     *
     * @param downInfo
     */
    private void downLoad2(DownLoadInfo downInfo) {
        DownLoadListener downLoadListener = new DownLoadListener<DownLoadInfo>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPause() {
                Log.e(Tag, "onPause");
            }

            @Override
            public void onNext(DownLoadInfo s) {

            }

            @Override
            public void onDownLoad(long currentLength, long countLength) {
                tv_d_pg2.setText(String.valueOf(currentLength * 1.0f / countLength * 100));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onWaiting() {
                tv_d_pg2.setText("等待中...");
            }
        };

        DownLoadManager.getInstance(this).startDownLoad(downInfo, downLoadListener);
    }

    private String imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imagePath = c.getString(columnIndex);
            Log.e(Tag, "imagePath:" + ("file://" + imagePath));
            upFile(imagePath);
            c.close();
        }
    }

    private void upFile(String imagePath) {
        Log.e(Tag, "开始上传");
        String up_url = "http://i1.sdblo.xyz:8091/up/";
        File file = new File(imagePath);
        Log.e(Tag, "file.length()：" + file.length());
        Map<String, Object> param = new HashMap<>();
        param.put("uid", 123789);
        List<MultipartBody.Part> files = new ArrayList<>();
        ProgressRequestBody progressBody = new ProgressRequestBody(RequestBody.create(MediaType.parse("multipart/form-data"), file));
        MultipartBody.Part part = MultipartBody.Part.createFormData("file_", file.getName(), progressBody);
        files.add(part);
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(this).build();
        retrofitNetUtils.uploadFileTest(up_url, param, files, new UploadProgressCallBack<ExtendsApiResult<String>>(progressBody) {
            @Override
            public void onSuccess(ExtendsApiResult<String> result) {
                try {
                    Log.e(Tag, "成功：" + result.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ApiException e) {
                Log.e(Tag, "错误1111：" + e.getMessage());
            }

            @Override
            public void onProgress(Long current, Long total) {
                tv_pg.setText((int) (current * 1.0f / total * 100) + "");
                Log.e(Tag, "上传中:current" + current + ",total:" + total);
            }

        });
    }
}
