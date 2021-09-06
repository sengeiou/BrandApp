
package brandapp.isport.com.basicres.mvp;

import androidx.lifecycle.Lifecycle;
import android.content.Context;
import android.text.TextUtils;

import com.isport.brandapp.basicres.R;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import brandapp.isport.com.basicres.BaseFragment;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.ToastUtils;


/**
 * ClassName:BaseMVPFragment <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年1月10日 下午4:13:18 <br/>
 *
 * @author Administrator
 */
public abstract class BaseMVPFragment<V extends BaseView, P extends BasePresenter<V>>
        extends BaseFragment implements BaseView {
    // Persenter类的实例。
    public P mFragPresenter;

    @Override
    public void onAttach(Context context) {
        mFragPresenter = createPersenter();
        if (mFragPresenter != null) {
            // BasePersenter类的方法。主要用于将View用弱引用赋值给P层的View对象
            mFragPresenter.attach(context, (V) this);
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        if (mFragPresenter != null) {
            // BasePersenter类的方法。主要用于将View的引用清除。
            mFragPresenter.detach();
        }
        super.onDetach();
    }

    // 子类实现，具体类型创建具体P层对象。
    protected abstract P createPersenter();

    @Override
    public void onRespondError(String message) {
        netError();
        if (StringUtil.isBlank(message)||message.contains("没有访问权限！")){
            return;
        }
            ToastUtils.showToast(context, TextUtils.isEmpty(message) ? getString(R.string.common_please_check_that_your_network_is_connected) : message);
    }

    /**
     * 绑定生命周期
     */
    @Override
    public <X> AutoDisposeConverter<X> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }
}