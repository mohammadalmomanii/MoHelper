package com.devmo.mohelper;


import com.devmo.mohelper.api.model.Data;

public interface MainInterface {
    default void onItemClick(Data data, int position){}
    default void onItemStatusClick(Data data, int position){}
    default void onCustomDialogItemClick(){}
    default void onItemLongClick(Data data, int position){}
    default void onContextMenuSelected(Data data){}
    default void onItemDelete(Data data,int position){}
    default void onItemApprove(Data data,int position){}
    default void onItemReview(Data data,int position){}
    default void onItemSave(Data data, int position){}
    default void onSearch(String text,String type){}
    default void onDialogDismiss(Data data, int position){}
    default void checkCameraPermission(Data data, int position){}
    default void showImage(Object image){}

}
