package bike.gymproject.viewlibray.pickerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import bike.gymproject.viewlibray.R;
import bike.gymproject.viewlibray.pickerview.adapter.ArrayWheelAdapter;
import bike.gymproject.viewlibray.pickerview.listener.OnItemSelectedListener;
import bike.gymproject.viewlibray.pickerview.view.WheelView;

/*
 *
 * classes : com.jkcq.gym.view.pickerview
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/22 16:46
 */
public class DyncArrayPickerView extends LinearLayout {

    private WheelView arrayWheelView1, arrayWheelView2;
    private ItemSelectedValue itemSelectedValue;

    /**
     * 设置是否循环滚动
     */
    private boolean cyclic = true;

    private List<String> listSource;
    private List<String> listSource2;

    public DyncArrayPickerView(Context context) {
        this(context, null);
    }

    public DyncArrayPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DyncArrayPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DyncArrayPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        initView();
        setListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_dync_picker_array, this, true);

        arrayWheelView1 = (WheelView) findViewById(R.id.array_picker1);
        arrayWheelView2 = (WheelView) findViewById(R.id.array_picker2);
    }

    private void initData() {

    }

    public void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
        arrayWheelView1.setCyclic(cyclic);
        arrayWheelView2.setCyclic(cyclic);
    }

    public void setSelectItem(int index) {
        arrayWheelView1.setCurrentItem(index);
    }

    int startHour, endHour, startMin, endMin;

    public void setData(String time, int starHour, int endHour, int startMin, int endMin) {
        int hourIndex = 0;
        int minIndex = 0;
        this.startHour = starHour;
        this.endHour = endHour;
        this.startMin = startMin;
        this.endMin = endMin;
        try {
            listSource = new ArrayList<>();
            String[] times = time.split(":");
            int hour = Integer.parseInt(times[0]);
            int min = Integer.parseInt(times[1]);
            hourIndex=hour-starHour;
            minIndex=min-startMin;
            for (int i = starHour; i <= endHour; i++) {
                listSource.add(i + "");
                /*if (hour == i) {
                    hourIndex = i - starHour;
                }*/
            }

            listSource2 = new ArrayList<>();
            for (int i = startMin; i <= endMin; i++) {
                listSource2.add(i + "");
               /* if (min == i) {
                    minIndex = i = startMin;
                }*/
            }

        } catch (Exception e) {

        } finally {
            ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter<String>((ArrayList<String>) listSource);
            arrayWheelView1.setAdapter(arrayWheelAdapter);
            ArrayWheelAdapter arrayWheelAdapter2 = new ArrayWheelAdapter<String>((ArrayList<String>) listSource2);
            arrayWheelView2.setAdapter(arrayWheelAdapter2);
            arrayWheelView1.setCurrentItem(hourIndex);
            arrayWheelView2.setCurrentItem(minIndex);
            arrayWheelView1.setLabel(getContext().getString(R.string.pickerview_hours));
            arrayWheelView2.setLabel(getContext().getString(R.string.pickerview_minutes));
        }

    }

    public void setItemOnclick(ItemSelectedValue itemOnclick) {
        itemSelectedValue = itemOnclick;
    }

    public void setData(ArrayList<String> items) {
        listSource = items;
        ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter<String>(items);
        arrayWheelView1.setAdapter(arrayWheelAdapter);
    }

    private void setListener() {
        arrayWheelView1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (itemSelectedValue != null) {
                    itemSelectedValue.onItemSelectedValue(getItem());
                }
            }
        });
        arrayWheelView2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (itemSelectedValue != null) {
                    itemSelectedValue.onItemSelectedValue2(getItem());
                }
            }
        });
    }


    public interface ItemSelectedValue {
        public void onItemSelectedValue(String str);

        public void onItemSelectedValue2(String str);
    }

    public int getCurrentItem() {
        return arrayWheelView1.getCurrentItem();
    }

    public String getTime() {
        StringBuffer buffer = new StringBuffer();
        //Log.e("getTime", "arrayWheelView1.getCurrentItem()=" + arrayWheelView1.getCurrentItem() + "arrayWheelView1.getCurrentItem() + startHour=" + arrayWheelView1.getCurrentItem() + startHour);
        buffer.append(String.format("%02d", (arrayWheelView1.getCurrentItem() + startHour))).append(":")
                .append(String.format("%02d", (arrayWheelView2.getCurrentItem() + startMin)));
        return buffer.toString();
    }

    public String getItem() {
        if (null == listSource || listSource.isEmpty()) {
            return null;
        }
        int position = arrayWheelView1.getCurrentItem();
        if (position < 0 || position >= listSource.size()) {
            return null;
        }

        return listSource.get(position);
    }
}