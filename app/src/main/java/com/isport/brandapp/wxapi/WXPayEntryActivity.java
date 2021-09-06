package com.isport.brandapp.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import brandapp.isport.com.basicres.commonutil.Logger;

/*
 * 微信支付回调页面
 *
 * @author mhj
 * Create at 2017/10/30 14:17
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //api = WXAPIFactory.createWXAPI(this, AuthLoginHelper.APP_ID_WX);
       // api.registerApp(AuthLoginHelper.APP_ID_WX);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Logger.e("code:" + baseResp.errCode);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //这里就是支付完成后需要做的事，如跳到哪个页面啥的
            if (baseResp.errCode == 0) {
                //TODO 支付成功
            } else if (baseResp.errCode == -1) {//支付流程错误 签名 appId等不正确

            } else if (baseResp.errCode == -2) { //用户取消支付

            }
        }
    }
}
