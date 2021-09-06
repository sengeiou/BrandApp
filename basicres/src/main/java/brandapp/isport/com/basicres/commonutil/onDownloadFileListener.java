package brandapp.isport.com.basicres.commonutil;

/*
 *
 *
 * @author mhj
 * Create at 2019/3/26 14:21
 */
public interface onDownloadFileListener {
    void onStart(float length);
    void onProgress(float progress);
    void onComplete();
    void onFail();
}
