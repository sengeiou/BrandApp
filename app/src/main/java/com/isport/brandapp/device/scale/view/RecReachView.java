package com.isport.brandapp.device.scale.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.isport.brandapp.R;
import com.isport.brandapp.device.bracelet.bean.StepBean;

import java.util.ArrayList;
import java.util.Collections;

import bike.gymproject.viewlibray.RecView;

public class RecReachView extends LinearLayout {
    Context mContext;

    RecView recView1, recView2, recView3, recView4, recView5, recView6, recView7;

    ArrayList<RecView> recViews;
    ArrayList<Long> values;

    View line;

    Float[][] value = {{0f, 18.5f}, {18.5f, 24.0f}, {24.0f, 28.0f}, {28.0f, 32.0f}, {32.0f, 50.0f}};
    float goalValue;

    public RecReachView(Context context) {
        this(context, null);
    }

    public RecReachView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecReachView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
       /* TypedArray a = context.obtainStyledAttributes(attrs, bike.gymproject.viewlibray.R.styleable.ItemView,
                defStyleAttr, 0);
        titleText = a.getString(bike.gymproject.viewlibray.R.styleable.ItemView_itemText);
        titleColor = a.getColor(bike.gymproject.viewlibray.R.styleable.ItemView_itemTextColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));

        contentText = a.getString(bike.gymproject.viewlibray.R.styleable.ItemView_contentText);
        contentColor = a.getColor(bike.gymproject.viewlibray.R.styleable.ItemView_contentTextColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));
        showRemind = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showRemind, false);
        showArrow = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showArrow, true);
        showBottomLine = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showBottomLine, false);
        showCheckModel = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showCheckModel, false);
        isChecked = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_isChecked, false);
        remindIcon = a.getDrawable(bike.gymproject.viewlibray.R.styleable.ItemView_remindIcon);
        a.recycle();*/

        initView();
        //setListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();


    }


    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_rec_reach, this, true);
        recView1 = findViewById(R.id.recview1);
        recView2 = findViewById(R.id.recview2);
        recView3 = findViewById(R.id.recview3);
        recView4 = findViewById(R.id.recview4);
        recView5 = findViewById(R.id.recview5);
        recView6 = findViewById(R.id.recview6);
        recView7 = findViewById(R.id.recview7);
        recViews = new ArrayList<>();
        recViews.add(recView1);
        recViews.add(recView2);
        recViews.add(recView3);
        recViews.add(recView4);
        recViews.add(recView5);
        recViews.add(recView6);
        recViews.add(recView7);
        line = findViewById(R.id.line);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (right != 0 && bottom != 0) {
            line.layout(left, top, right, bottom);
        }

    }


    private void initData() {

    }


    int left = 0;
    int right = 0;
    int top = 0;
    int bottom = 0;

    public void setCurrentBMIvalue(int goalValue, ArrayList<StepBean> stepBeans) {
        this.values = new ArrayList<>();
        for (int i = 0; i < stepBeans.size(); i++) {
            values.add(stepBeans.get(i).getStep());
        }
        long max = Collections.max(values);
        if (goalValue >= max) {
            max = goalValue;
        }

        this.goalValue = goalValue;
        int view1Height = recView1.getHeight();

        int imageWidth = line.getWidth();
        int imageHeight = line.getHeight();

        left = line.getLeft();
        right = left + imageWidth;
        if (max == goalValue) {
            top = 0;
        } else {
            top = (int) (view1Height * (1.0f * (max - goalValue) / max));
        }
        bottom = top + imageHeight;
        line.layout(left, top, right, bottom);

        for (int i = 0; i < values.size(); i++) {
            float height = view1Height * (1.0f * values.get(i) / max);
            recViews.get(i).setResultTextView(values.get(i) >= goalValue ? R.color.common_view_color : R.color.common_main_nor_press, height);
            //  recViews.get(i).isShowDate(true);
        }
        if (recViews.size() < values.size()) {
            recViews.get(recViews.size() - 1).isShowDate(true);
        } else {
            recViews.get(values.size() - 1).isShowDate(true);
        }
    }

}
