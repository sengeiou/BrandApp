package com.isport.brandapp.sport.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class QuickMultipleEntity implements MultiItemEntity {

    static final int TITLE = 1;
    static final int CONTENT = 2;

    public int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }
}
