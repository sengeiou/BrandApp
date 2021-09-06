package phone.gym.jkcq.com.socialmodule.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import brandapp.isport.com.basicres.BaseActivity;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonview.RoundImageView;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.manager.QRCodeManager;

public class FriendQRCodeActivity extends BaseActivity {

    private ImageView iv_back;
    private ImageView iv_qrcode;
    private TextView tv_nickname;
    private RoundImageView iv_head;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_q_r_code;
    }

    @Override
    protected void initView(View view) {
//        DecodeHintType.valueOf(DecodeHintType.PURE_BARCODE);
        iv_back = findViewById(R.id.iv_back);
        tv_nickname = findViewById(R.id.tv_nickname);
        iv_head = findViewById(R.id.iv_head);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_qrcode = findViewById(R.id.iv_qrcode);
        iv_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                writeQRCode();
//            ScanQRCode();
//                startActivity(new Intent(FriendQRCodeActivity.this,FriendScanActivity.class));

            }
        });
        //  StatusBarCompat.setStatusBarColor(getWindow(), getResources().getColor(R.color.transparent), true);

    }

    private UserInfoBean mUserInfo;
    private QRCodeManager mQRCodeManager;

    @Override
    protected void initData() {
        mUserInfo = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(this));
        if (mUserInfo != null) {
            tv_nickname.setText(mUserInfo.getNickName());
            LoadImageUtil.getInstance().loadCirc(FriendQRCodeActivity.this, mUserInfo.getHeadUrl(), iv_head);
        }
//        mQRCodeManager = new QRCodeManager(this);
        writeQRCode();
    }

    private void writeQRCode() {
        String qrString = FriendConstant.QR_HEAD + mUserInfo.getQrString();
        int width = DisplayUtils.dip2px(FriendQRCodeActivity.this,
                285);
        iv_qrcode.setImageBitmap(mQRCodeManager.createQRCodeBitmap(qrString, width, width));

//        mWriter = new BarcodeWriter();
//        mQRCodeManager.writeQRCode(qrString, iv_qrcode);
    }


    @Override
    protected void initEvent() {

    }

    @Override
    protected void initHeader() {

    }

}
