package brandapp.isport.com.basicres.mvp;

import android.os.Bundle;
import android.text.TextUtils;

import com.isport.brandapp.basicres.R;

import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.ToastUtils;


/**
 * ClassName:BaseActActivity <br/>
 * Function: MVP模式 Activity基类，继承BaseActivity. T ：为View层的实现类。 P为Persenter的具体实现类<br/>
 * Date: 2016年12月23日 下午1:58:53 <br/>
 *
 * @author Administrator
 */
public abstract class BaseMVPTitleActivity<V extends BaseView, P extends BasePresenter<V>>
        extends BaseTitleActivity implements BaseView {

    // Persenter类的实例。
    public P mActPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d("BaseMVPTitleActivity", "onCreate");
        mActPresenter = createPresenter();
        if (mActPresenter != null) {
            // BasePersenter类的方法。主要用于将View用弱引用赋值给P层的View对象
            mActPresenter.attach(this, (V) this);
        }
        super.onCreate(savedInstanceState);
    }

    // 子类实现，具体类型创建具体P层对象。
    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        if (mActPresenter != null) {
            // BasePersenter类的方法。主要用于将View的引用清除。
            mActPresenter.detach();
        }
        super.onDestroy();
    }

    @Override
    public void onRespondError(String message) {
        netError(BaseMVPTitleActivity.this);
        if (StringUtil.isBlank(message)||message.contains("没有访问权限！")){
            return;
        }
            ToastUtils.showToast(context, TextUtils.isEmpty(message) ? getString(R.string.common_please_check_that_your_network_is_connected) : message);
    }
}