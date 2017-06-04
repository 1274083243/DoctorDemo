package com.ike.doctordemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.ike.commonutils.commonUtils.BezierViewUtils.ViewPath;
import com.ike.commonutils.commonUtils.BezierViewUtils.ViewPathEvaluator;
import com.ike.commonutils.commonUtils.BezierViewUtils.ViewPoint;

import java.util.ArrayList;
import java.util.List;

/**
* author ike
* create time 17:52 2017/5/22
* function: 贝塞尔曲线demo
**/

public class BezierPathDemo extends View{

    private Paint mPaint;
    private Paint linePaint;
    private int width;
    private int height;
    private int radus=500;
    private ViewPoint current_position;
    private List<ViewPoint> points=new ArrayList<>();
    private boolean isFinish;
    private ViewPathEvaluator evaluator;
    private String Tag="BezierPathDemo";
    private float evaluatorT;

    public BezierPathDemo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierPathDemo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(2);
        linePaint.setStyle(Paint.Style.STROKE);
    }
    private void initPath(){
        ViewPath path=new ViewPath();
        path.moveTo(width/2,height/2);
        path.lineTo(width/2-radus/2,height/2);
        path.quadTo(width/2-radus,height/2-radus,width/2,height/2-radus);
        path.quadTo(width/2+radus,height/2-radus,width/2,height/2);
        evaluator = new ViewPathEvaluator();
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, path.getPoints().toArray());
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                 long time=0;
                ViewPoint newPoint= (ViewPoint) valueAnimator.getAnimatedValue();
                if (valueAnimator.getCurrentPlayTime()>3000){
                    time=3000;
                }else {
                    time=valueAnimator.getCurrentPlayTime();
                }
                evaluatorT=time*1.0f/Long.valueOf(3000);
                Log.e(Tag,"evaluatorT:"+evaluatorT);
                points.add(newPoint);
                current_position=newPoint;
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isFinish=true;
                invalidate();
            }
        });

        animator.start();
    }

    private Path path=new Path();
    private float defaultRadus=25;
    @Override
    protected void onDraw(Canvas canvas) {
        if (evaluatorT<=0.5){
            defaultRadus=25+evaluatorT*200;
        }else {
            defaultRadus=25+(1-evaluatorT)*200;
        }
        canvas.drawCircle(current_position.x,current_position.y,defaultRadus,mPaint);
        defaultRadus=25;
        if (isFinish){
            path.moveTo(points.get(0).x,points.get(0).y);
            for(ViewPoint point:points){
                path.lineTo(point.x,point.y);
                isFinish=false;
            }
            canvas.drawPath(path,linePaint);
        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
        current_position =new ViewPoint(width/2,height/2);
    }
    class ViewObject{
        private View view;

        public ViewObject(View view) {
            this.view = view;
        }
        public void setChangeProperty(ViewPoint newPoint){
            view.setTranslationX(newPoint.x);
            view.setTranslationY(newPoint.y);
        }

    }
public void startAnimator(){
    initPath();
}

}
