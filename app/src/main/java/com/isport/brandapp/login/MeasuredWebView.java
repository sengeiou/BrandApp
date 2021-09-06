package com.isport.brandapp.login;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MeasuredWebView extends WebView {
    public MeasuredWebView(Context context) {
        super(context);
    }

    public MeasuredWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasuredWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 计算webView内容的真实宽度
     * @return
     */
    public int getMeasureContentWidth() {
        return computeHorizontalScrollRange();
    }

    /**
     * 计算webView内容的真实高度
     * @return
     */
    public int getMeasureContentHeight() {
        return computeVerticalScrollRange();
    }
}
