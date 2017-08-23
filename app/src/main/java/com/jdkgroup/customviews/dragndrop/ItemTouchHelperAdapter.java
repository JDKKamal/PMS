package com.jdkgroup.customviews.dragndrop;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
     void onDrop(int fromPosition, int toPosition);
}