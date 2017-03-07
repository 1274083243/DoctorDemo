package com.ike.commonutils.widget.loopViewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ike.commonutils.R;

import java.util.List;

/**
作者：ike
时间：2017/3/7 15:26
功能描述：广告轮播图的封装
**/

public class LooperViewPager extends RelativeLayout{
    private static ViewPager looper_vp;
    private static TextView tv_des;//描述文本
    private static Handler handler;
    private View rootView;
    private static LooperTask mTask;
    private static int  TIME_INTERVAL;//轮训时间间隔
    private static boolean AUTO_LOOP;//是否允许自动轮训
    private static List<LoopModel> mModels;//轮训数据实体
    private static Context context;
    private static GestureDetectorCompat gestureDetectorCompat;//手势监听
    public LooperViewPager(Context context) {
        this(context,null);
    }

    public LooperViewPager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LooperViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rootView=View.inflate(context, R.layout.looper_viewpager_layout,this);
        looper_vp= (ViewPager) rootView.findViewById(R.id.looper_vp);
        tv_des= (TextView) rootView.findViewById(R.id.tv_des);
        handler=new Handler(Looper.getMainLooper());
        this.context=context;
    }

    /**
     * 初始化
     */
    private static void init() {
        looper_vp.setAdapter(new LooperPagerAdapter());
        initListener();
        // 给ViewPager设置中间选中的值
        int middle = Integer.MAX_VALUE / 2;
        int extra = middle % mModels.size();
        looper_vp.setCurrentItem(middle - extra);
        if (AUTO_LOOP){
            ViewPagerScroller viewPagerScroller = new ViewPagerScroller(context);
            viewPagerScroller.initViewPagerScroll(looper_vp);
            mTask=new LooperTask();
            mTask.startLoop();
        }
        tv_des.setText(mModels.get(0).text_des);

    }
    public static class LooperTask implements Runnable{
        public void startLoop(){
            stopLoop();
            handler.postDelayed(mTask,TIME_INTERVAL);
        }
        public void stopLoop(){
            handler.removeCallbacks(mTask);
        }
        @Override
        public void run() {
            int currentItem=looper_vp.getCurrentItem();
            looper_vp.setCurrentItem(++currentItem,true);
            handler.postDelayed(mTask,TIME_INTERVAL);
        }
    }
    public static class Builder{
        /**
         * 设置轮训时间
         * @param looperTime
         * @return
         */
        public Builder setLooperTime(int looperTime){
            TIME_INTERVAL=looperTime;
            return Builder.this;
        }

        /**
         * 设置是否允许自动轮训
         * @param allowAutoLoop
         * @return
         */
        public Builder setAllowAutoLoop(boolean allowAutoLoop){
            AUTO_LOOP=allowAutoLoop;
            return this;
        }
        public Builder build(List<LoopModel> models){
            mModels=models;
            init();
            return this;
        }
    }
    public Builder getBuilder(){
        return new Builder();
    }
    static class LooperPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            if (mModels!=null&&mModels.size()>0){
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=position%mModels.size();
            ImageView imageView=new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(Integer.valueOf(mModels.get(position).iconUrl));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 初始化事件监听
     */
    private static void initListener(){
        looper_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                position=position%mModels.size();
                tv_des.setText(mModels.get(position).text_des);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        gestureDetectorCompat=new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }


            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                final int newPosition = looper_vp.getCurrentItem() % mModels.size();
                if (listner!=null){
                    listner.onItemClick(mModels.get(newPosition),newPosition);
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    /**
     * 轮播图条目单击事件监听
     */
    private static OnItemClickListener listner;
    public interface OnItemClickListener{
        void onItemClick(LoopModel model,int postion);
    }
    public void setOnItemClickListener(OnItemClickListener listner){
        this.listner=listner;
    }
}
