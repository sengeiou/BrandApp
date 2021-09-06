package com.isport.blelibrary.observe.bean;

public class ResultBean {

    public int dataType;
    public int isSuccess;


    public ResultBean(int dataType, int isSuccess) {
        this.dataType = dataType;
        this.isSuccess = isSuccess;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }
}
