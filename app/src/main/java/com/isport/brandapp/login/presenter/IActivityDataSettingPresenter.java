package com.isport.brandapp.login.presenter;

public interface IActivityDataSettingPresenter {
    void getCustomerBasicInfo();
    void saveUserBaseicInfo(String sex, String name, String height, String weight, String defaultDay);
}
