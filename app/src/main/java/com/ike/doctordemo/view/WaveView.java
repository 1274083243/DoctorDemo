package com.ike.doctordemo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;

/**
* author ike
* create time 12:12 2017/5/23
* function: 自定义水波纹进度条
**/
public class WaveView extends View {
    private String Tag="WaveView";

    private Paint mWavePaint;
    private int height;
    private int width;
    private Shader mShader;
    // 水波进度
    private final float DEFAULT_LEVEL_RATIO = 0.0f;
    private float mWaveLevelRatio = DEFAULT_LEVEL_RATIO;
    // 水波高度
    private final float DEFAULT_AMPLITUDE_RATIO = 0.05f;
    private float mWaveAmplitudeRatio = DEFAULT_AMPLITUDE_RATIO;
    private Paint mWaveBorderPaint;
    private Path abovePath;
    private Path behindPath;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mWavePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(Color.RED);
        mWavePaint.setStyle(Paint.Style.FILL);

        mWaveBorderPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mWaveBorderPaint.setColor(Color.RED);
        mWaveBorderPaint.setStrokeWidth(5);
        mWaveBorderPaint.setStyle(Paint.Style.STROKE);
        //前景波浪
        abovePath = new Path();
        //后景波浪
        behindPath = new Path();
    }
    private void getWaveOffset() {
        if (behiadeOffset > Float.MAX_VALUE - 100) {
            behiadeOffset = 0;
        } else {
            behiadeOffset += 1;
        }

        if (aboveOffset > Float.MAX_VALUE - 100) {
            aboveOffset = 0;
        } else {
            aboveOffset += 1;
        }
    }
    /**
     * 绘制静态的水波纹
     */
    double amplitude= 30;
    private float aboveOffset=0;
    private float behiadeOffset=0;
    private void drwaNomalWave(){
        abovePath.reset();
        behindPath.reset();
//        我们知道正弦曲线的公式为：y=Asin(ωx+φ)+k
//        A：振幅，控制曲线的起伏高度
//        ω：周期or频率，控制一个周期的宽度
//        φ：相位，控制曲线在X轴的偏移
//        k ：控制曲线在Y轴的偏移
        //ω 频率
        double frequency = 2 * Math.PI / width;
        //振幅
        getWaveOffset();
        //高度的增长量K
        float level= 100;

        abovePath.moveTo(0,height);
        behindPath.moveTo(0,height);
        for (int x=0;x<=width;x+=20){
            //计算每一个点的坐标
            float y= (float) (amplitude*Math.sin(frequency*x)+aboveOffset)+level;
            float y1= (float) (amplitude*Math.sin(frequency*x+behiadeOffset))+level;
            abovePath.lineTo(x,y);
            behindPath.lineTo(x,y1);
        }
        abovePath.lineTo(width+1,height);
        behindPath.lineTo(width+1,height);

//        mShader = new BitmapShader(waveBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
//        mWavePaint.setShader(mShader);
    }
    // 位移的比例（相对于控件宽度），默认1.0f没有位移。
    private float mWaveTranslateRatio = 1.0f;
    private Matrix mShaderMatrix = new Matrix();
    // 水波周期
    private float mWaveCycleRatio = 1.0f;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWavePaint.setColor(Color.BLUE);
        canvas.drawPath(behindPath,mWavePaint);
        mWavePaint.setColor(Color.RED);
        canvas.drawPath(abovePath,mWavePaint);
        int  boder= (int) mWaveBorderPaint.getStrokeWidth();
//        mShaderMatrix.setTranslate(mWaveTranslateRatio * getWidth(),
//                (DEFAULT_LEVEL_RATIO - mWaveLevelRatio) * getHeight());
//        mShaderMatrix.postScale(mWaveCycleRatio, mWaveAmplitudeRatio / DEFAULT_AMPLITUDE_RATIO, 0, getHeight() * (1 - mWaveLevelRatio));
//        mShader.setLocalMatrix(mShaderMatrix);
//        canvas.drawCircle(width/2,height/2,Math.min(width/2,height/2)-boder,mWavePaint);
//
//        canvas.drawCircle(width/2,height/2,Math.min(width/2,height/2)-boder/2,mWaveBorderPaint);

    }
    /**
     * 设置水波进度
     * @param waveLevelRatio 区间 0.0f~1.0f
     */
    public void setWaveLevelRatio(float waveLevelRatio) {
        if(mWaveLevelRatio != waveLevelRatio) {
            mWaveLevelRatio = waveLevelRatio;
            invalidate();
        }
    }
    public void setWaveTranslateRatio(float waveTranslateRatio){
        if (waveTranslateRatio!=this.mWaveTranslateRatio&&waveTranslateRatio>0){
            this.mWaveTranslateRatio=waveTranslateRatio;
            invalidate();
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
        aboveOffset= (float) (width/4* (2 * Math.PI / width));
        Log.e(Tag,"aboveOffset:"+aboveOffset);
        //createShader();

    }
    // y=Asin(ωx+φ)+k
    private void createShader() {
        int height = getHeight();
        int width = getWidth();
        // ω周期  让一个周期的宽度正好是width
        double frequency = 2 * Math.PI / width;
        // A振幅  默认的振幅是高度的0.05f
        float amplitude = height * DEFAULT_AMPLITUDE_RATIO;
        // k（y轴偏移量，进度） 默认的进度是50%
        float level = height * DEFAULT_LEVEL_RATIO;

        Bitmap waveBitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(waveBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        Path abovePath = new Path();
        Path behindPath = new Path();
        abovePath.moveTo(0, height);
        behindPath.moveTo(0, height);
        for(int x = 0; x<=width; x+=20) {
            // y=Asin(ωx+φ)+k
            float aboveY = (float) (amplitude * Math.sin(frequency * x))+ level;
            // 背面的水波偏移一些，和前面的错开。
            float behindY = (float) (amplitude * Math.sin(frequency * x + width/4*frequency))+ level;
            abovePath.lineTo(x, aboveY);
            behindPath.lineTo(x, behindY);
        }
        abovePath.lineTo(width + 1, height);
        behindPath.lineTo(width + 1, height);
        paint.setColor(Color.BLUE);
        canvas.drawPath(behindPath, paint);
        paint.setColor(Color.RED);
        canvas.drawPath(abovePath, paint);

        mShader = new BitmapShader(waveBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setShader(mShader);
    }
    public void startAnimation(){
        ObjectAnimator animator=ObjectAnimator.ofFloat(this,"waveTranslateRatio",0.0f,1.0f);
        animator.setDuration(3000);
        ObjectAnimator animatorleve=ObjectAnimator.ofFloat(this,"waveLevelRatio",0.0f,1.0f);
        animatorleve.setDuration(3000);
        AnimatorSet set=new AnimatorSet();
        set.playTogether(animator,animatorleve);
        set.start();

    }
    private class RefreshProgressRunnable implements Runnable {
        public void run() {
            synchronized (WaveView.this) {
                long start = System.currentTimeMillis();

                drwaNomalWave();

                invalidate();

                long gap = 16 - (System.currentTimeMillis() - start);
                postDelayed(this, gap < 0 ? 0 : gap);
            }
        }
    }
    private RefreshProgressRunnable mRefreshProgressRunnable;
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (View.GONE == visibility) {
            removeCallbacks(mRefreshProgressRunnable);
        } else {
            removeCallbacks(mRefreshProgressRunnable);
            mRefreshProgressRunnable = new RefreshProgressRunnable();
            post(mRefreshProgressRunnable);
        }
    }
}
