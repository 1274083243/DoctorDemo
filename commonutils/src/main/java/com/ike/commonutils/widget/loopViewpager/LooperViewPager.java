package com.ike.commonutils.widget.loopViewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ike.commonutils.R;
import com.ike.commonutils.commonUtils.DpPxUtils;

import java.util.List;

/**
作者：ike
时间：2017/3/7 15:26
功能描述：广告轮播图的封装
**/

public class LooperViewPager extends RelativeLayout{
    private String Tag="LooperViewPager";
    private static ViewPager looper_vp;
    private static Handler handler;
    private View rootView;
    private static LinearLayout point_container;
    private static LooperTask mTask;
    private static int  TIME_INTERVAL;//轮训时间间隔
    private static boolean AUTO_LOOP;//是否允许自动轮训
    private static List<LoopModel> mModels;//轮训数据实体
    private static Context context;
    private static int select_drawable;
    private static int un_select_drawable;
    private static final int RES_DEFAULT=-1;//资源默认值
    private static GestureDetectorCompat gestureDetectorCompat;//手势监听
    public LooperViewPager(Context context) {
        this(context,null);
    }

    public LooperViewPager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LooperViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initView(context);

    }

    /**
     * 初始化自定义属性
     * @param context
     * @param attrs
     */
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LooperViewPager);
        AUTO_LOOP=typedArray.getBoolean(R.styleable.LooperViewPager_auto_loop,false);
        TIME_INTERVAL=typedArray.getInt(R.styleable.LooperViewPager_loop_time,RES_DEFAULT);
        select_drawable=typedArray.getResourceId(R.styleable.LooperViewPager_select_icon,RES_DEFAULT);
        un_select_drawable=typedArray.getResourceId(R.styleable.LooperViewPager_unselect_icon,RES_DEFAULT);
        typedArray.recycle();
    }

    /**
     * 初始化布局
     * @param context
     */
    private void initView(Context context) {
        rootView= View.inflate(context, R.layout.looper_viewpager_layout,this);
        looper_vp= (ViewPager) rootView.findViewById(R.id.looper_vp);
        handler=new Handler(Looper.getMainLooper());
        point_container= (LinearLayout) findViewById(R.id.point_container);
        this.context=context;
    }

    /**
     * 初始化
     */
    private static void init() {
        looper_vp.setAdapter(new LooperPagerAdapter());
        initListener();
        addPointToContainer();
        // 给ViewPager设置中间选中的值
        int middle = Integer.MAX_VALUE / 2;
        int extra = middle % mModels.size();
        looper_vp.setCurrentItem(middle - extra);
        if (AUTO_LOOP&&TIME_INTERVAL!=RES_DEFAULT){
            ViewPagerScroller viewPagerScroller = new ViewPagerScroller(context);
            viewPagerScroller.initViewPagerScroll(looper_vp);
            mTask=new LooperTask();
            mTask.startLoop();
        }


    }

    /**
     * 添加指示的下标
     */
    private static void addPointToContainer(){
        point_container.removeAllViews();
        int size=mModels.size();
        for (int i=0;i<size;i++){
            ImageView imageView=new ImageView(context);
            imageView.setImageResource(select_drawable);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DpPxUtils.dip2px(10,context),DpPxUtils.dip2px(10,context));
            if (i!=0){
                params.leftMargin=DpPxUtils.dip2px(10,context);
                imageView.setImageResource(un_select_drawable);
            }
            point_container.addView(imageView,params);
        }

    }

    /**
     * 轮询任务
     */
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
         * 设置是否允许自动轮训
         * @param allowAutoLoop:是否允许自动轮询,looperTime轮轮询间隔时间
         * @return
         */
        public Builder setAllowAutoLoop(boolean allowAutoLoop,int looperTime){
            AUTO_LOOP=allowAutoLoop;
            TIME_INTERVAL=looperTime;
            return this;
        }

        /**
         * 设置轮询数据
         * @param models
         * @return
         */
        public Builder build(List<LoopModel> models){
            mModels=models;
            init();
            return this;
        }

        /**
         * 设置轮询点击事件监听
         * @param mlistner
         * @return
         */
        public Builder  setOnItemClickListener(OnItemClickListener mlistner){
            listner=mlistner;
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
                changeSelctIcon(position);
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
     * 切换被选中的图片
     * @param position
     */
    private static void changeSelctIcon(int position) {
        int count=point_container.getChildCount();
        for (int i=0;i<count;i++){
            ImageView view= (ImageView) point_container.getChildAt(i);
            if (i==position){
                view.setImageResource(select_drawable);
            }else {
                view.setImageResource(un_select_drawable);
            }
        }
    }

    /**
     * 轮播图条目单击事件监听
     */
    private static OnItemClickListener listner;
    public interface OnItemClickListener{
        void onItemClick(LoopModel model,int postion);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mModels!=null&&mModels.size()>0){
            gestureDetectorCompat.onTouchEvent(event);
            if (mTask!=null){
                switch (event.getAction()) {
                    //手指按下
                    case MotionEvent.ACTION_DOWN:
                        mTask.stopLoop();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mTask.startLoop();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
