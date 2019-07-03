package com.url.msi.uixy;

public interface ItemTouchHelper {
    //移动
    void onItemMove(int startPostion,int endPosttion);
    //删除
    void onItemDele(int postion);
}
