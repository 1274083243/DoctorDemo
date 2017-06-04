package com.ike.commonutils.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
* author ike
* create time 9:13 2017/5/20
* function:自定义流式布局
**/
public class FlowLayout extends FrameLayout{
    private String TAG="FlowLayout";
    private List<PositionInfo> viewPositions=new ArrayList<>();
    public FlowLayout(@NonNull Context context) {
        this(context,null);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewPositions.clear();
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeHeight=MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight=MeasureSpec.getMode(heightMeasureSpec);
        int modeWidth=MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth=MeasureSpec.getSize(widthMeasureSpec);
        int line_width=0;//每行的宽度
        int line_height=0;//每行的高度
        int child_width=0;//子view的宽度
        int child_height=0;//子view的高度
        int final_width=0;//控件最终宽度
        int final_height=0;//控件最终高度
        int childCount=getChildCount();
        if (childCount==0){
            return;
        }
        //循环遍历子view进行子view的高度.宽度测量计算
        for (int i=0;i<childCount;i++){
            View childView=getChildAt(i);
            FrameLayout.LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
            measureChild(childView,MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
            child_width=layoutParams.leftMargin+layoutParams.rightMargin+childView.getMeasuredWidth();
            child_height=layoutParams.topMargin+layoutParams.bottomMargin+childView.getMeasuredHeight();
            //判断是否该换行了
            //换行
            if(child_width+line_width>sizeWidth){
                //记录当前行的宽度:两种可能性
                //1：当前行只有一个view，其宽度达到了sizeWidth，则进行换行
                //2:当前行的多个view宽度进行叠加后达到了sizeWidth，则进行换行
                final_width=Math.max(line_width,child_width);
                //高度进行叠加
                final_height+=line_height;
                //初始化下一行的宽高
                line_height=child_height;
                line_width=child_width;
                PositionInfo info=new PositionInfo(
                        layoutParams.leftMargin,
                        final_height+layoutParams.topMargin,
                        childView.getMeasuredWidth()+layoutParams.rightMargin,
                        final_height+childView.getMeasuredHeight()+layoutParams.bottomMargin);
                viewPositions.add(info);
            }else {
                //宽度进行叠加
                line_height=Math.max(line_height,child_height);
                PositionInfo info=new PositionInfo(
                        line_width+layoutParams.leftMargin,
                        final_height+layoutParams.topMargin,
                        line_width+childView.getMeasuredWidth()+layoutParams.rightMargin,
                        final_height+childView.getMeasuredHeight()+layoutParams.bottomMargin);
                viewPositions.add(info);
                line_width+=child_width;
            }
            //如果是到达了最后一个子view
            if (i==childCount-1){
                final_width=Math.max(line_width,final_width);
                final_height+=line_height;
            }
        }
        setMeasuredDimension(
                modeWidth==MeasureSpec.EXACTLY?sizeWidth:final_width,
                modeHeight==MeasureSpec.EXACTLY?sizeHeight:final_height
                );
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount=viewPositions.size();
       if (childCount==0){
           return;
       }
        for (int i=0;i<childCount;i++){
            View childView=getChildAt(i);
            if (childView.getVisibility()==GONE){
                continue;
            }
            PositionInfo info=viewPositions.get(i);

            childView.layout(info.left,info.top,info.right,info.bottom);
        }
    }

    /**
     * 每个view的位置信息
     */
    class PositionInfo{
        int left;
        int top;
        int right;
        int bottom;

        public PositionInfo(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }


    }

}
