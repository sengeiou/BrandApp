package brandapp.isport.com.basicres.net.userNet;

import brandapp.isport.com.basicres.entry.bean.OssBean;
import brandapp.isport.com.basicres.mvp.BaseView;

public interface CommonAliView extends BaseView {

    public void successUpgradeImageUrl(String pathUrl);

    public void successGetAliToken(OssBean ossBean);

    public void upgradeProgress(long currentSize, long totalSize);

    public void onFailAliOptin(int type);


}
