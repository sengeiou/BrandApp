package phone.gym.jkcq.com.socialmodule.activity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.adapter.MyActionAdapter;
import phone.gym.jkcq.com.socialmodule.adapter.TestAdapter;

public class TestActivity extends AppCompatActivity {

    private ViewPager2 viewpager_test;
    private MyActionAdapter mMyActionAdapter;

    private TabLayout tab_layout;
    private String[] mTitles = new String[]{"作品","动态", "喜欢"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        viewpager_test = findViewById(R.id.viewpager_test);
        tab_layout = findViewById(R.id.tab_layout);
        initViewPager();

    }

    private void initViewPager() {
//        mProductionAdapter=new ProductionAdapter(getSupportFragmentManager(),this.getLifecycle());
//        mProductionAdapter=new ProductionAdapter(this);
        viewpager_test.setAdapter(new TestAdapter(this));
        viewpager_test.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tab_layout, viewpager_test, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mTitles[position]);
            }
        });
        tabLayoutMediator.attach();
    }
}
