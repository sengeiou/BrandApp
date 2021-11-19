package test;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.brandapp.R;

import java.util.Arrays;
import java.util.List;

import bike.gymproject.viewlibray.WatchStepChartView;
import brandapp.isport.com.basicres.BaseActivity;

/**
 * Created by Admin
 * Date 2021/8/30
 */
public class Test2Activity extends BaseActivity {


    private WatchStepChartView testWatchStepChartView;
    private EditText testInputEdit;

    private Button getVersionBtn;

    public void test2SendData1(View view){
        ISportAgent.getInstance().requestBle(BleRequest.READ_DEVICE_GOAL);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_layout_test2;
    }

    @Override
    protected void initView(View view) {
        testWatchStepChartView = findViewById(R.id.testWatchStepChartView);
        testInputEdit = findViewById(R.id.testInputEdit);

        getVersionBtn = findViewById(R.id.getVersionBtn);

        getVersionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVersionData();
            }
        });

    }

    @Override
    protected void initData() {
        showChartView();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initHeader() {

    }

    private void getVersionData(){
        ISportAgent.getInstance().requestBle(BleRequest.Common_GetVersion);
    }


    private void showChartView(){
        Float[] stepArrays = new Float[]{800f,650f,300f,1500f,0f,0f,730f};
        List<Float> resultList =  Arrays.asList(stepArrays);

        testWatchStepChartView.setList(resultList);
    }



    public void testSendNotice(View view){
        String inputStr = testInputEdit.getText().toString();

        if(TextUtils.isEmpty(inputStr))
            return;
        int numberV = Integer.parseInt(inputStr);


        ISportAgent.getInstance().requestBle(0x01, numberV,"这是title", "这是content");
    }
}
