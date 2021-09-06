package com.isport.blelibrary.result.impl.scale;

import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/11
 * @Fuction
 */

public class ScaleCalculateResult implements IResult,Serializable{

    @Override
    public String getType() {
        return SCALE_ALCULATE_DATA;
    }

    private Scale_FourElectrode_DataModel mScale_FourElectrode_DataModel;

    @Override
    public String toString() {
        return "ScaleCalculateResult{" +
                "mScale_FourElectrode_DataModel=" + mScale_FourElectrode_DataModel +
                '}';
    }

    public Scale_FourElectrode_DataModel getScale_FourElectrode_DataModel() {
        return mScale_FourElectrode_DataModel;
    }

    public void setScale_FourElectrode_DataModel(Scale_FourElectrode_DataModel scale_FourElectrode_DataModel) {
        mScale_FourElectrode_DataModel = scale_FourElectrode_DataModel;
    }

    public ScaleCalculateResult(Scale_FourElectrode_DataModel scale_FourElectrode_DataModel) {

        mScale_FourElectrode_DataModel = scale_FourElectrode_DataModel;
    }
}
