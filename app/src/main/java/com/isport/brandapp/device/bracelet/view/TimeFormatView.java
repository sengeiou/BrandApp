package com.isport.brandapp.device.bracelet.view;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface TimeFormatView extends BaseView {

    public void successGetTimeFormat(int format);

    public void successTimeFormat(boolean isSave);

}
