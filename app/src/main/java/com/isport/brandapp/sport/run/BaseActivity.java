package com.isport.brandapp.sport.run;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.isport.brandapp.R;

import bike.gymproject.viewlibray.TitleView;

/**
 * 功能:
 */
public abstract class BaseActivity<P extends BasePresenter, V> extends AppCompatActivity {

    private P mPresenter;
    private View view;
    private TitleView tlTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
//        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
//        //做状态栏透明沉浸
//        StatusBarUtil.setTranslucentStatus(this);
//        //设置状态栏白底黑字
//        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
//            StatusBarUtil.setStatusBarColor(this,0x55000000);
//        }


        mPresenter = initPresenter();
        mPresenter.attachView((V) this);
        if (mPresenter != null) {
            mPresenter.setUIEnable(true);
        }
        onCreateTitle(getTitleLayout());
        onCreateActivity(savedInstanceState);
    }


    @Override
    public void setContentView(int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null, false));
    }

    @Override
    public void setContentView(View views) {
        view = getLayoutInflater().inflate(R.layout.activity_base, null);
        tlTitle = view.findViewById(R.id.tlTitle);
        LinearLayout content = view.findViewById(R.id.ll_base_content);
        content.addView(views, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        super.setContentView(view);


    }


    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    public TitleView getTitleLayout() {
        return tlTitle;
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter.setUIEnable(false);
            mPresenter.onDetach();
        }
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        if (mPresenter != null) {
            mPresenter.setUIEnable(true);
            mPresenter.onResume();
            mPresenter.onVisible();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mPresenter != null) {
            mPresenter.onPause();

        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mPresenter != null) {
            //mPresenter.setUIEnable(false);
            mPresenter.onStop();
            mPresenter.onInvisible();
        }
        super.onStop();
    }

    public P getPresenter() {
        return mPresenter;
    }

    protected abstract P initPresenter();

    protected abstract int initLayout();

    protected abstract void onCreateActivity(Bundle savedInstanceState);

    /**
     * 不强制要求子类实现 但子类可以重写
     *
     * @param titleLayout
     */
    protected void onCreateTitle(TitleView titleLayout) {
        setTitle(getTitle());//默认标题名为AndroidManifest 的Activity标签的label
        // titleLayout.setle(this);//默认左边按钮是finish
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 界面已销毁
     *
     * @return
     */
    public boolean isFinished() {
        return isDestroyed() || isFinishing();
    }
}