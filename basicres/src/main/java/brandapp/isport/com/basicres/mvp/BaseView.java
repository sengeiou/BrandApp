
package brandapp.isport.com.basicres.mvp;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * ClassName:BaseView <br/>
 * Function: Activity层的View接口基类。主要用于规范BasePersenter 和 BaseMvpActivity接口定义. <br/>
 * Date: 2016年12月23日 上午11:02:48 <br/>
 *
 * @author Administrator
 */
public interface BaseView {

    void onRespondError(String message);

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();

}
