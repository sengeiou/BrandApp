package brandapp.isport.com.basicres.entry.bean;

import brandapp.isport.com.basicres.entry.bean.UpdatePhotoBean;

public class PhotoEventBean {

    public UpdatePhotoBean bean;

    public PhotoEventBean () {
    }
    public PhotoEventBean (UpdatePhotoBean bean) {
        this.bean=bean;
    }

    @Override
    public String toString() {
        return "PhotoEventBean{" +
                "bean=" + bean +
                '}';
    }
}
