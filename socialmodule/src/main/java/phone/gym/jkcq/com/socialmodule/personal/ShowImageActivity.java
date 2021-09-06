package phone.gym.jkcq.com.socialmodule.personal;

import android.view.View;
import android.widget.ImageView;


import brandapp.isport.com.basicres.BaseActivity;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import phone.gym.jkcq.com.socialmodule.R;

public class ShowImageActivity extends BaseActivity {
    ImageView imageView;
    String picList;

    @Override
    protected int getLayoutId() {
        return R.layout.friend_app_show_image;
    }

    @Override
    protected void initView(View view) {

        imageView = view.findViewById(R.id.iv_pic);
        //imageView.setMaxZoom(4f);

    }

    @Override
    protected void initData() {

        picList = getIntent().getStringExtra("pic_list");
        if (picList != null) {
            LoadImageUtil.getInstance().loadCirc(context, picList, imageView);
        }
    }

    @Override
    protected void initEvent() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initHeader() {
      //  StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.common_white));
    }
}
