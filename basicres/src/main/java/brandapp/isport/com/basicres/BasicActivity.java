package brandapp.isport.com.basicres;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import brandapp.isport.com.basicres.commonutil.Logger;


public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
        // 友盟添加界面統計
        String name = this.getClass().getName();
        Logger.i("tag", name);
        //MobclickAgent.onPageEnd(name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟添加界面統計
        //MobclickAgent.onResume(this);
        String name = this.getClass().getName();
        //MobclickAgent.onPageStart(name);
    }

    public <X> AutoDisposeConverter<X> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }
}