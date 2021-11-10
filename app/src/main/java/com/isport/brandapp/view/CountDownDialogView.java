package com.isport.brandapp.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.isport.brandapp.R;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 倒计时dialog，全屏显示背景
 * Created by Admin
 * Date 2021/11/3
 */
public class CountDownDialogView extends Dialog {

    private static final String TAG = "CountDownDialogView";

    private TextView countTv;

    private Context context;

    public OnCusCountDownCompleteListener onCusCountDownCompleteListener;

    public void setOnCusCountDownCompleteListener(OnCusCountDownCompleteListener onCusCountDownCompleteListener) {
        this.onCusCountDownCompleteListener = onCusCountDownCompleteListener;
    }

    public CountDownDialogView(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public CountDownDialogView(Context context) {
        this(context,R.style.Dialog_Fullscreen);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.cus_count_down_dialog_view);


        initViews();
    }

    private void initViews() {
        countTv = findViewById(R.id.cusCountDownTv);
    }


    public void startShow(){


        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(4).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull Long aLong) {
                Log.e(TAG,"-----onNext="+aLong);
                int countValue = (int) (3-aLong);
                countTv.setText(countValue == 0 ? "GO":countValue+"");
                countTv.setAnimation(setAnim());
                countTv.startAnimation(setAnim());
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                if(onCusCountDownCompleteListener != null)
                    onCusCountDownCompleteListener.onCountDownComplete();
            }
        });
    }


    public AnimationSet setAnim() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        // 渐变时间
        alphaAnimation.setDuration(1000);
        ScaleAnimation animation = new ScaleAnimation(0.0f, 2.6f, 0.0f, 2.6f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);
        return animationSet;
        // updateSoundAndView(millisUntilFinished, animationSet);
    }



    public interface OnCusCountDownCompleteListener{
        void onCountDownComplete();
    }
}
