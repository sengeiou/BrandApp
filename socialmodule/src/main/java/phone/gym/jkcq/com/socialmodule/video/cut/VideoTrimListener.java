package phone.gym.jkcq.com.socialmodule.video.cut;

/**
 * Author：J.Chou
 * Date：  2016.08.01 2:23 PM
 * Email： who_know_me@163.com
 * Describe:
 */
public interface VideoTrimListener {
    void onStartTrim();
    void onTriming(int progress);
    void onFinishTrim(String url);
    void onCancel();
    void onFailed();
}
