package com.ike.doctordemo.widget.courseTableLayout;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ike.commonutils.widget.widgetUtils.recyclerViewUtils.SpaceItemDecoration;
import com.ike.commonutils.widget.widgetUtils.recyclerViewUtils.ViewHolder;
import com.ike.doctordemo.R;

import java.util.List;

/**
 * 作者：ike
 * 时间：2017/3/9 10:47
 * 功能描述：课程表控件
 **/
public class CourseTableLayout extends LinearLayout {
    private String Tag="CourseTableLayout";
    private LinearLayout dateLayout;
    private LinearLayout courseLayout;
    private RecyclerView mCorseView;
    private  final int type_index=1;
    private final int type_course=2;
    public CourseTableLayout(Context context) {
        this(context, null);
    }

    public CourseTableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseTableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        //初始化课程表上端的日期控件：同来标注星期与日期
        dateLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams dataLayoutParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getContext(),30));
        dateLayout.setLayoutParams(dataLayoutParam);
        addView(dateLayout,0);
        //初始化课程表下端的课程信息容器
        courseLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams courseLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1.0f);
        courseLayout.setLayoutParams(courseLayoutParam);
        addView(courseLayout,1);

    }
    /**
     * 初始化recycleView
     */
    private void initRecycleView(List<String> dateData) {
        mCorseView=new RecyclerView(getContext());
        GridLayoutManager manager=new GridLayoutManager(getContext(),15);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
              if((position%8)==0){
                  return type_index;
              }else {
                  return type_course;
              }
            }
        });
        mCorseView.setLayoutManager(manager);
        mCorseView.addItemDecoration(new SpaceItemDecoration(2, 8));
        courseLayout.addView(mCorseView);
        corseAdapter = new CorseAdapter(dateData);
        mCorseView.setAdapter(corseAdapter);
    }
    private CorseAdapter corseAdapter;
    /**
     * 开始构建课程表
     */
    public void buildCourseTableLayout(List<String> dateData,List<String> corseData){
        View view=new View(getContext());
        LinearLayout.LayoutParams viewLayoutParam=new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
        view.setLayoutParams(viewLayoutParam);
        view.setBackgroundColor(getResources().getColor(R.color.blue_main_theme));
        dateLayout.addView(view);
        for (int i=0;i<dateData.size();i++){
            addDateTable(dateData.get(i),i);
        }
        initRecycleView(corseData);
    }
    /**
     * 添加日期Item
     */
    private void addDateTable(String data,int index){
        TextView tv_data=new TextView(getContext());
        if (index%2!=0){
            tv_data.setBackgroundColor(getResources().getColor(R.color.blue_main_theme));
        }else {
            tv_data.setBackgroundColor(Color.RED);
        }
        LinearLayout.LayoutParams tvLayoutParam=new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,2.0f);
        tv_data.setLayoutParams(tvLayoutParam);
        tv_data.setGravity(Gravity.CENTER);
        tv_data.setTextSize(TypedValue.COMPLEX_UNIT_SP,14.0f);
        tv_data.setText(data);
        dateLayout.addView(tv_data);
    }
    class CorseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public int postion;

        public CorseAdapter(List<String> mdata) {
            this.mdata = mdata;
        }
        private List<String> mdata;
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder=null;
            switch (viewType){
                case type_index:
                    holder=ViewHolder.createViewHolder(parent.getContext(),parent,R.layout.item_index);
                    break;
                case type_course:
                    holder=ViewHolder.createViewHolder(parent.getContext(),parent,R.layout.item_course);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder mHolder= (ViewHolder) holder;
            mHolder.setText(R.id.tv,mdata.get(position));
            if (position%2==0){
                mHolder.setBackgroundRes(R.id.tv,R.color.blue_main_theme);
            }else {
                mHolder.setBackgroundRes(R.id.tv,R.color.gray_20);
            }
            this.postion=position;
        }

        @Override
        public int getItemViewType(int position) {
            if (position%8==0){
                return type_index;
            }else {
                return type_course;
            }

        }

        @Override
        public int getItemCount() {
            if (mdata!=null&&mdata.size()>0){
                return mdata.size();
            }
            return 0;
        }
    }

}
