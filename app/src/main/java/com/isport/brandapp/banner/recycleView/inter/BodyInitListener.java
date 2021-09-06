package com.isport.brandapp.banner.recycleView.inter;

import android.content.Context;

import com.isport.brandapp.banner.recycleView.holder.CustomHolder;

import java.util.List;

public interface BodyInitListener<T> {

    public CustomHolder getHolderByViewType(Context context, List<T> lists, int itemID);
}
