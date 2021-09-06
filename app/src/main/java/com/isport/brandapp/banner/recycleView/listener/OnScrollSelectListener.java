package com.isport.brandapp.banner.recycleView.listener;


public interface OnScrollSelectListener<T> {

   void onPositionChenge(int position, T data);
   void onStopPosition(int position, T data);

}
