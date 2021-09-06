package com.isport.brandapp.Third_party_access;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.tasks.Task;
import com.isport.brandapp.R;
import com.isport.brandapp.Third_party_access.util.GoogleFitUtil;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;

import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;

public class GoogleFitActivity extends BaseTitleActivity {
    TextView tvCon;
    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_google_fit;
    }

    @Override
    protected void initView(View view) {

        tvCon = view.findViewById(R.id.btn_conn);
    }

    @Override
    protected void initData() {

        titleBarView.setTitle(R.string.google_fit_con);
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {

            }
        });
    }

    @Override
    protected void initEvent() {

        tvCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectGoogle();
            }
        });

    }

    @Override
    protected void initHeader() {

    }

    FitnessOptions fitnessOptions = FitnessOptions.builder()
            //心率
            .addDataType(DataType.TYPE_HEART_POINTS, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.AGGREGATE_HEART_POINTS, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_WRITE)
            .build();

    public void connectGoogle() {
        try {
            //如果已经登录过google账号。则可以拿到账号
            account = GoogleSignIn.getLastSignedInAccount(this);
            if (account == null) {
                //没有。则要登录
                signIn();
            } else {
                //有，则要订阅
                sunbscrerib();
                //GoogleFitUtil.insertData(getActivity());
                // sunbscrerib();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    GoogleSignInAccount account;


    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void sunbscrerib() {
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            return;
        }
        //判断是否有写入数据的权限，这个会弹出授权写入数据的弹框
        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    REQUEST_OAUTH_REQUEST_CODE,
                    account,
                    fitnessOptions);
        } else {
            ToastUtil.showTextToast(this, UIUtils.getString(R.string.google_fit_success));
            GoogleFitUtil.insertData(this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_OAUTH_REQUEST_CODE:
                handleSignInResult(requestCode, data);
        }
    }


    /**
     * 在activity的onActivityResult方法里面调用，回调是否登录账号成功。
     *
     * @param data
     */
    int GOOGLE_SIGN_IN = 1009;

    public void handleSignInResult(int requestCode, Intent data) {
        if (requestCode != GOOGLE_SIGN_IN) {
            return;
        }

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        handleResult(task);
    }

    int index = 0;

    private void handleResult(Task<GoogleSignInAccount> googleData) {
        try {
            GoogleSignInAccount signInAccount = googleData.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (signInAccount != null && index == 0) {
                Log.e("account", "si:" + "\n" + signInAccount.getEmail());
                String str = signInAccount.getEmail() + "\n"
                        + signInAccount.getId() + "\n" +
                        signInAccount.getAccount().name + "\n" +
                        signInAccount.getDisplayName() + "\n" +
                        signInAccount.getGivenName() + "\n";
                Log.e("account", "str:" + str + "\n");
                //sunbscrerib();
                sunbscrerib();
                //textView.setText(str);
            } else {
                Log.e("account", "si为空:" + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("account", "si异常:" + "\n");
        }

    }
}
