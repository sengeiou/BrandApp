package test;

import android.util.Log;
import android.view.View;

import com.isport.brandapp.R;
import com.isport.brandapp.wu.util.HeartRateConvertUtils;

import java.util.ArrayList;
import java.util.List;

import bike.gymproject.viewlibray.LineChartView;
import bike.gymproject.viewlibray.LineRecChartView;
import bike.gymproject.viewlibray.LineScrollChartView;
import bike.gymproject.viewlibray.bean.HrlineBean;
import bike.gymproject.viewlibray.chart.LineChartEntity;
import brandapp.isport.com.basicres.BaseActivity;

/**
 * Created by Admin
 * Date 2021/8/25
 */
public class ActivityTest1 extends BaseActivity {

    private LineScrollChartView lineScrollChartView;


    private LineChartView lineChartView;
    private LineRecChartView lineRecChartView1,lineRecChartView2,lineRecChartView3,lineRecChartView4,lineRecChartView5,lineRecChartView6;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test1_layout;
    }

    @Override
    protected void initView(View view) {
        lineScrollChartView = findViewById(R.id.testStepView);
        lineChartView = findViewById(R.id.lineChartView);

        lineRecChartView1 = findViewById(R.id.lineChartView1);
        lineRecChartView2 = findViewById(R.id.lineChartView2);
        lineRecChartView3 = findViewById(R.id.lineChartView3);
        lineRecChartView4 = findViewById(R.id.lineChartView4);
        lineRecChartView5 = findViewById(R.id.lineChartView5);
        lineRecChartView6 = findViewById(R.id.lineChartView6);

    }

    @Override
    protected void initData() {
        List<LineChartEntity> datas = new ArrayList<>();
        datas.add(new LineChartEntity(60, 1, (float) 0));
        int maxHr = 90;
        int minHr = 60;
        datas.add(new LineChartEntity(61,1, (float) 100));
        datas.add(new LineChartEntity(62,1, (float) 0));
        datas.add(new LineChartEntity(63,1, (float) 60));

        lineChartView.setData(datas, true, maxHr, minHr);
        lineScrollChartView.setData(datas,true,maxHr,minHr,1,datas.size());

        ArrayList<HrlineBean> lists = HeartRateConvertUtils.pointToheartRateR(25, "Female");


        lineRecChartView1.setData(datas, true, maxHr, minHr, lists.get(0));
        lineRecChartView2.setData(datas, true, maxHr, minHr, lists.get(1));
        lineRecChartView3.setData(datas, true, maxHr, minHr, lists.get(2));
        lineRecChartView4.setData(datas, true, maxHr, minHr, lists.get(3));
        lineRecChartView5.setData(datas, true, maxHr, minHr, lists.get(4));
        lineRecChartView6.setData(datas, true, maxHr, minHr, lists.get(5));

        lineScrollChartView.setOnlister(new LineScrollChartView.onSecletValueClick() {
            @Override
            public void onSelectValue(String value) {
                Log.e(TAG,"-------滑动="+value);
            }
        });

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initHeader() {

    }
}
